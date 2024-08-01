package com.rumor.yumback.domains.oauth2.dto;

import com.rumor.yumback.enumeration.UserRole;

public record Oauth2UserDto(
        UserRole role,
        String name,
        String username,
        String picture
) {

}
