package com.rumor.yumback.domains.posts.application;

import com.rumor.yumback.domains.comments.infrastructure.CommentLikeJpaRepository;
import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.posts.domain.PostLike;
import com.rumor.yumback.domains.posts.infrastructure.PostJpaRepository;
import com.rumor.yumback.domains.posts.infrastructure.PostLikeJpaRepository;
import com.rumor.yumback.domains.posts.presentation.view.PostDetailView;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostJpaRepository postJpaRepository;
    private final PostLikeJpaRepository postLikeJpaRepository;
    private final CommentLikeJpaRepository commentLikeJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public CommunityDto community(Pageable pageable) {
        Page<PostDto> popular = postJpaRepository.findAll(PageRequest.of(0, 3, Sort.by("likeCount").descending()))
                .map(PostDto::from);

        List<PostDto> posts = postJpaRepository.findAll(Sort.by("createdAt").descending()).stream()
                .map(PostDto::from)
                .toList();

        return new CommunityDto(popular, posts);
    }

    public Post register(PostRegisterDto postRegisterDto, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Post post = new Post(postRegisterDto.title(), postRegisterDto.category(), "", postRegisterDto.contents(), foundUser);
        return postJpaRepository.save(post);
    }

    public PostDetailView post(UUID id, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Post foundPost = postJpaRepository.findByIdAndCreator(id, foundUser)
                .orElseThrow(() -> new RuntimeException("not found post"));

        Boolean isLiked = postLikeJpaRepository.findByPostAndUser(foundPost, foundUser).isPresent();
        PostDetailDto postDetailDto = PostDetailDto.from(foundPost, isLiked);

        // 멱등성, 단일책임 위배
        foundPost.increaseViewCount();
        return PostDetailView.from(postDetailDto, foundUser);
    }

    public String likes(UUID id, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Post foundPost = postJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found post"));

        return postLikeJpaRepository.findByPostAndUser(foundPost, foundUser)
                .map(postLike -> {
                    foundPost.decreaseLikeCount();
                    postLikeJpaRepository.delete(postLike);
                    return "좋아요가 취소되었습니다.";
                })
                .orElseGet(() -> {
                    foundPost.increaseLikeCount();
                    postLikeJpaRepository.save(new PostLike(foundPost, foundUser));
                    return "좋아요를 누르셨습니다.";
                });
    }
}
