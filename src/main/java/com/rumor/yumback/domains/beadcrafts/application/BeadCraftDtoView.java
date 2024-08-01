package com.rumor.yumback.domains.beadcrafts.application;

import com.rumor.yumback.common.UuidConverter;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class BeadCraftDtoView {
    private UUID id;
    private String name;
    private String category;
    private String picture;
    private String creatorName;
    private Long likeCount;
    private boolean isLiked;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public BeadCraftDtoView(BeadCraftDto beadCraftDto) {
        this.id = UuidConverter.fromBytes(beadCraftDto.getBeadCraftId());
        this.name = beadCraftDto.getBeadCraftName();
        this.category = beadCraftDto.getBeadCraftCategory();
        this.picture = beadCraftDto.getBeadCraftPicture();
        this.creatorName = beadCraftDto.getCreatorName();
        this.likeCount = beadCraftDto.getLikeCount();
        this.isLiked = beadCraftDto.getIsLiked();
        this.createdAt = beadCraftDto.getCreatedAt();
        this.updateAt = beadCraftDto.getUpdatedAt();
    }

}
