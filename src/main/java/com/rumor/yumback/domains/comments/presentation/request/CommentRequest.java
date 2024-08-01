package com.rumor.yumback.domains.comments.presentation.request;

import com.rumor.yumback.domains.comments.application.CommentDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CommentRequest(
        @NotEmpty String contents,
        UUID postId,
        UUID userId
) {
    public CommentDto toDto() {
        return new CommentDto(contents, postId, userId);
    }
}
