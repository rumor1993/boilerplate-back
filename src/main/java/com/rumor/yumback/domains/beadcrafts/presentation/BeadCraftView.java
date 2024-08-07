package com.rumor.yumback.domains.beadcrafts.presentation;

import com.querydsl.core.annotations.QueryProjection;
import com.rumor.yumback.domains.beadcrafts.domain.BeadCraft;
import com.rumor.yumback.domains.users.presentation.view.UserView;
import com.rumor.yumback.enumeration.BeadCraftCategory;

import java.time.LocalDateTime;
import java.util.UUID;

public record BeadCraftView(
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
    public BeadCraftView(UUID id, String name, BeadCraftCategory category, String picture, UserView creator, Long likeCount, Boolean isLiked, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public static BeadCraftView of (BeadCraft beadCraft) {
        return new BeadCraftView(beadCraft.getId(), beadCraft.getName(), beadCraft.getCategory(), beadCraft.getPicture(), UserView.from(beadCraft.getCreator()), null, null, beadCraft.getCreatedAt(), beadCraft.getUpdatedAt());
    }
}
