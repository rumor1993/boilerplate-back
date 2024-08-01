package com.rumor.yumback.domains.posts.presentation;

import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import com.rumor.yumback.domains.posts.application.PostService;
import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.posts.presentation.request.PostRequest;
import com.rumor.yumback.domains.posts.presentation.view.PostView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public Page<PostView> posts(Pageable pageable) {
        return postService.posts(pageable)
                .map(PostView::of);
    }

    @GetMapping("/{id}")
    public PostView post(@PathVariable UUID id) {
        return PostView.of(postService.post(id));
    }

    @PostMapping
    public PostView register(@RequestBody PostRequest postRequest, @AuthenticationPrincipal CustomOauth2User customOauth2User) {
        Post savedPost = postService.register(postRequest.toDto(), customOauth2User.getUsername());
        return PostView.of(savedPost);
    }
}
