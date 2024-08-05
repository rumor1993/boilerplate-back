package com.rumor.yumback.domains.posts.presentation.view;

import com.rumor.yumback.enumeration.PostCategory;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostDetailView(
        UUID id,
        String title,
        PostCategory category,
        String contents,
        String username,
        Long viewCount,
        Long commentCount,
        Long likeCount,
        Boolean isLiked,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}

