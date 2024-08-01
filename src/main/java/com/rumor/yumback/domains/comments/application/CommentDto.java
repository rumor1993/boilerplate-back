package com.rumor.yumback.domains.comments.application;

import java.util.UUID;

public record CommentDto(
        String contents,
        UUID postId,
        UUID userId) {
}
