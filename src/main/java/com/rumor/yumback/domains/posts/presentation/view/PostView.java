package com.rumor.yumback.domains.posts.presentation.view;

import com.rumor.yumback.enumeration.PostCategory;
import com.rumor.yumback.utils.PostUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostView(
        UUID id,
        String title,
        PostCategory category,
        String contents,
        String username,
        Long viewCount,
        Long commentCount,
        Long likeCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public String getImage() {
        return PostUtils.extractImage(this.contents);
    }

    public String getDescription() {
        return PostUtils.extractDescription(this.contents, 200);
    }

}
