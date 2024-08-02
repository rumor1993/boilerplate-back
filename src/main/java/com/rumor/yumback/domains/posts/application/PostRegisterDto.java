package com.rumor.yumback.domains.posts.application;


import com.rumor.yumback.enumeration.PostCategory;

import java.util.UUID;

public record PostRegisterDto(
        String title,
        PostCategory category,
        String description,
        String contents,
        UUID userId
) {
}
