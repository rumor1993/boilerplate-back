package com.rumor.yumback.domains.beadsCrafts.presentation;

import com.querydsl.core.annotations.QueryProjection;
import com.rumor.yumback.domains.beadsCrafts.domain.BeadsCraft;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.presentation.view.UserView;
import com.rumor.yumback.enumeration.BeadCraftCategory;

import java.time.LocalDateTime;
import java.util.UUID;

public record BeadsCraftView(
        UUID id,
        String name,
        BeadCraftCategory category,
        String picture,
        UserView creator,
        Long likeCount,
        Boolean isLiked,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
    @QueryProjection
    public BeadsCraftView(UUID id, String name, BeadCraftCategory category, String picture, UserView creator, Long likeCount, Boolean isLiked, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.picture = picture;
        this.creator = creator;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static BeadsCraftView from(BeadsCraft beadsCraft) {
        return from(beadsCraft, null);
    }

    public static BeadsCraftView from(BeadsCraft beadsCraft, User foundUser) {
        boolean isLiked = false;

        if (foundUser != null) {
            isLiked = beadsCraft.getLikes().
                    stream().anyMatch(beadCraftLikes -> beadCraftLikes.getUser()
                            .getId().equals(foundUser.getId()));
        }

        return new BeadsCraftView(
                beadsCraft.getId(),
                beadsCraft.getName(), beadsCraft.getCategory(),
                beadsCraft.getPicture(),
                UserView.from(beadsCraft.getCreator()),
                beadsCraft.getLikeCount(),
                isLiked,
                beadsCraft.getCreatedAt(),
                beadsCraft.getUpdatedAt());
    }
}
