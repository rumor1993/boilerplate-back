package com.rumor.yumback.domains.beadsCrafts.presentation;

import com.querydsl.core.annotations.QueryProjection;
import com.rumor.yumback.domains.beadsCrafts.domain.BeadsCraft;
import com.rumor.yumback.domains.posts.application.PostDetailDto;
import com.rumor.yumback.domains.posts.presentation.view.PostDetailView;
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
        String link,
        UserView creator,
        Long likeCount,
        Boolean isLiked,
        PostDetailView post,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
    @QueryProjection
    public BeadsCraftView(UUID id, String name, BeadCraftCategory category, String picture, String link, UserView creator, Long likeCount, Boolean isLiked, PostDetailView post, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.picture = picture;
        this.link = link;
        this.creator = creator;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
        this.post = post;
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
                beadsCraft.getLink(),
                UserView.from(beadsCraft.getCreator()),
                beadsCraft.getLikeCount(),
                isLiked,
                PostDetailView.from(PostDetailDto.from(beadsCraft.getPost())),
                beadsCraft.getCreatedAt(),
                beadsCraft.getUpdatedAt());
    }
}
