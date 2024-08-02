package com.rumor.yumback.domains.posts.presentation.request;

import com.rumor.yumback.domains.posts.application.PostRegisterDto;
import com.rumor.yumback.enumeration.PostCategory;

import java.util.UUID;

public record PostRequest(
        String title,
        PostCategory category,
        String description,
        String contents,
        UUID userId
) {
    public PostRegisterDto toDto() {
        return new PostRegisterDto(title, category, description, contents, userId);
    }
}
