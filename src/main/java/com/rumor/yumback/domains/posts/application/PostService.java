package com.rumor.yumback.domains.posts.application;

import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.posts.infrastructure.PostJpaRepository;
import com.rumor.yumback.domains.posts.presentation.view.PostView;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public Page<Post> posts(Pageable pageable) {
        return postJpaRepository.findAll(pageable);
    }

    public Post register(PostDto postDto, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Post post = new Post(postDto.title(), postDto.category(), postDto.description(), postDto.contents(), foundUser);
        return postJpaRepository.save(post);
    }

    public Post post(UUID id) {
        return postJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found post"));
    }
}
