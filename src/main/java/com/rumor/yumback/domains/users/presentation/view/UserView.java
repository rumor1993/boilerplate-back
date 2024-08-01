package com.rumor.yumback.domains.users.presentation.view;

import com.querydsl.core.annotations.QueryProjection;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.enumeration.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserView(
        UUID id,
        String username,
        String name,
        String email,
        String picture,
        UserRole role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    @QueryProjection
    public UserView(UUID id, String username, String name, String email, String picture, UserRole role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserView of(User foundUser) {
        return new UserView(foundUser.getId(), foundUser.getUsername(), foundUser.getName(), foundUser.getEmail(), foundUser.getPicture(), foundUser.getRole(), foundUser.getCreatedAt(), foundUser.getUpdatedAt());
    }
}
