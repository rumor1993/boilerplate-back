package com.rumor.yumback.domains.posts.application;

import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.posts.domain.PostLikes;
import com.rumor.yumback.domains.posts.infrastructure.PostJpaRepository;
import com.rumor.yumback.domains.posts.infrastructure.PostLikeJpaRepository;
import com.rumor.yumback.domains.posts.infrastructure.PostQueryDslRepository;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostJpaRepository postJpaRepository;
    private final PostLikeJpaRepository postLikeJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public Page<Post> posts(Pageable pageable) {
        return postJpaRepository.findAll(pageable);
    }

    public Post register(PostRegisterDto postRegisterDto, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Post post = new Post(postRegisterDto.title(), postRegisterDto.category(), postRegisterDto.description(), postRegisterDto.contents(), foundUser);
        return postJpaRepository.save(post);
    }

    public PostDto post(UUID id, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Post foundPost = postJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found post"));

        List<PostLikes> likes = postLikeJpaRepository.findAllByPost(foundPost);
        Boolean isLiked = postLikeJpaRepository.findByPostAndUser(foundPost, foundUser).isPresent();

        foundPost.increaseViewCount();
        return PostDto.of(foundPost, (long) likes.size(), isLiked);
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
