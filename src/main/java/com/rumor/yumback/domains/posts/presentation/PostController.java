package com.rumor.yumback.domains.posts.presentation;

import com.rumor.yumback.common.SuccessResponse;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import com.rumor.yumback.domains.posts.application.CommunityDto;
import com.rumor.yumback.domains.posts.application.CommunityView;
import com.rumor.yumback.domains.posts.application.PostService;
import com.rumor.yumback.domains.posts.presentation.request.PostRequest;
import com.rumor.yumback.domains.posts.presentation.view.PostDetailView;
import com.rumor.yumback.domains.posts.presentation.view.PostView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public CommunityView community(Pageable pageable) {
        CommunityDto communityDto = postService.community(pageable);

        return new CommunityView(
                communityDto.populars()
                        .map(PostView::from),
                communityDto.posts().stream()
                        .map(PostView::from)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public PostDetailView post(@PathVariable UUID id, @AuthenticationPrincipal CustomOauth2User customOauth2User) {
        return postService.post(id, customOauth2User.getUsername());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse register(@RequestBody PostRequest postRequest, @AuthenticationPrincipal CustomOauth2User customOauth2User) {
        postService.register(postRequest.toDto(), customOauth2User.getUsername());
        return new SuccessResponse(HttpStatus.CREATED, "등록되었습니다.");
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<SuccessResponse> likes(@AuthenticationPrincipal CustomOauth2User customOauth2User, @PathVariable UUID id) {
        String likesMessage = postService.likes(id, customOauth2User.getUsername());
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.CREATED, likesMessage));
    }
}
