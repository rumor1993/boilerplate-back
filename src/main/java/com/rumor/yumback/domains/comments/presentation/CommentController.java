package com.rumor.yumback.domains.comments.presentation;

import com.rumor.yumback.domains.comments.application.CommentService;
import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.comments.domain.ReComment;
import com.rumor.yumback.domains.comments.presentation.request.CommentRequest;
import com.rumor.yumback.domains.comments.presentation.view.CommentView;
import com.rumor.yumback.domains.comments.presentation.view.ReCommentView;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ReCommentView reply(@RequestBody CommentRequest commentRequest, @AuthenticationPrincipal CustomOauth2User customOauth2User, @PathVariable UUID id) {
        ReComment savedComment = commentService.reply(id ,commentRequest.toDto(), customOauth2User.getUsername());
        return new ReCommentView(savedComment);
    }
}
