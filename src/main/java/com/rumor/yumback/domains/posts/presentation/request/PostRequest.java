package com.rumor.yumback.domains.posts.presentation.request;

import com.rumor.yumback.domains.posts.application.PostDto;
import com.rumor.yumback.enumeration.PostCategory;

import java.util.UUID;

public record PostRequest(
        String title,
        PostCategory category,
        String description,
        String contents,
        UUID userId
) {
    public PostDto toDto() {
        return new PostDto(title, category, description, contents, userId);
    }
}
