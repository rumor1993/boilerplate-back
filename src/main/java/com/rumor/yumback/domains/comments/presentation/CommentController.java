package com.rumor.yumback.domains.comments.presentation;

import com.rumor.yumback.common.SuccessResponse;
import com.rumor.yumback.domains.comments.application.CommentService;
import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.comments.domain.Reply;
import com.rumor.yumback.domains.comments.presentation.request.CommentRequest;
import com.rumor.yumback.domains.comments.presentation.view.CommentView;
import com.rumor.yumback.domains.comments.presentation.view.ReplyView;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public CommentView register(@Valid @RequestBody CommentRequest commentRequest, @AuthenticationPrincipal CustomOauth2User customOauth2User) {
        Comment savedComment = commentService.register(commentRequest.toDto(), customOauth2User.getUsername());
        return new CommentView(savedComment);
    }

    @PostMapping("/{id}/reply")
    public ReplyView reply(@RequestBody CommentRequest commentRequest, @AuthenticationPrincipal CustomOauth2User customOauth2User, @PathVariable UUID id) {
        Reply savedComment = commentService.reply(id ,commentRequest.toDto(), customOauth2User.getUsername());
        return new ReplyView(savedComment);
    }

    @PostMapping("/{id}/likes")
    public SuccessResponse likes(@AuthenticationPrincipal CustomOauth2User customOauth2User, @PathVariable UUID id) {
        String message = commentService.likes(customOauth2User.getUsername(), id);
        return new SuccessResponse(HttpStatus.CREATED, message);
    }
}
