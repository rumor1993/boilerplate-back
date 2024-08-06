package com.rumor.yumback.domains.comments.presentation;

import com.rumor.yumback.common.SuccessResponse;
import com.rumor.yumback.domains.comments.application.CommentService;
import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.comments.domain.ReComment;
import com.rumor.yumback.domains.comments.presentation.request.CommentRequest;
import com.rumor.yumback.domains.comments.presentation.view.CommentView;
import com.rumor.yumback.domains.comments.presentation.view.ReCommentView;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/recomments")
@RequiredArgsConstructor
public class ReCommentController {
    private final CommentService commentService;

    @PostMapping("/{id}/likes")
    public SuccessResponse likes(@AuthenticationPrincipal CustomOauth2User customOauth2User, @PathVariable UUID id) {
        String message = commentService.reCommentLikes(customOauth2User.getUsername(), id);
        return new SuccessResponse(HttpStatus.CREATED, message);
    }
}
