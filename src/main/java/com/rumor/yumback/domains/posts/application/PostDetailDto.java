package com.rumor.yumback.domains.posts.application;

import com.rumor.yumback.enumeration.PostCategory;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostDetailDto(
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

