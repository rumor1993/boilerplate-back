package com.rumor.yumback.domains.comments.presentation;

import com.rumor.yumback.common.SuccessResponse;
import com.rumor.yumback.domains.comments.application.CommentService;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {
    private final CommentService commentService;

    @PostMapping("/{id}/likes")
    public SuccessResponse likes(@AuthenticationPrincipal CustomOauth2User customOauth2User, @PathVariable UUID id) {
        String message = commentService.replyLike(customOauth2User.getUsername(), id);
        return new SuccessResponse(HttpStatus.CREATED, message);
    }
}
