package com.rumor.yumback.domains.posts.application;

import com.rumor.yumback.domains.comments.application.CommentService;
import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.posts.domain.PostLikes;
import com.rumor.yumback.domains.posts.infrastructure.PostJpaRepository;
import com.rumor.yumback.domains.posts.infrastructure.PostLikeJpaRepository;
import com.rumor.yumback.domains.posts.infrastructure.PostQueryDslRepository;
import com.rumor.yumback.domains.posts.presentation.view.PostDetailView;
import com.rumor.yumback.domains.posts.presentation.view.PostView;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostJpaRepository postJpaRepository;
    private final PostLikeJpaRepository postLikeJpaRepository;
    private final PostQueryDslRepository postQueryDslRepository;
    private final UserJpaRepository userJpaRepository;
    private final CommentService commentService;

    public CommunityView community(Pageable pageable) {
        List<PostView> populars = postQueryDslRepository.populars3();
        Page<PostView> posts = postQueryDslRepository.posts(pageable);
        return new CommunityView(populars, posts);
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

        Post foundPost = postJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found post"));

        foundPost.increaseViewCount();

        PostDetailDto postDetailDto = postQueryDslRepository.post(foundUser, id);
        return PostDetailView.from(postDetailDto, foundPost.getComments());

    }

    public String likes(UUID id, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Post foundPost = postJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found post"));

        return postLikeJpaRepository.findByPostAndUser(foundPost, foundUser)
                .map(postLikes -> {
                    unlikes(postLikes);
                    return "좋아요가 취소되었습니다.";
                })
                .orElseGet(() -> {
                    postLikeJpaRepository.save(new PostLikes(foundPost, foundUser));
                    return "좋아요를 누르셨습니다.";
                });
    }

    private void unlikes(PostLikes postLikes) {
        postLikeJpaRepository.delete(postLikes);
    }
}
