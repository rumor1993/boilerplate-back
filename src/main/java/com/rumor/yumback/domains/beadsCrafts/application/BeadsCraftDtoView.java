package com.rumor.yumback.domains.beadsCrafts.application;

import com.rumor.yumback.common.UuidConverter;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class BeadsCraftDtoView {
    private UUID id;
    private String name;
    private String category;
    private String picture;
    private String creatorName;
    private Long likeCount;
    private boolean isLiked;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public BeadsCraftDtoView(BeadsCraftDto beadsCraftDto) {
        this.id = UuidConverter.fromBytes(beadsCraftDto.getBeadCraftId());
        this.name = beadsCraftDto.getBeadCraftName();
        this.category = beadsCraftDto.getBeadCraftCategory();
        this.picture = beadsCraftDto.getBeadCraftPicture();
        this.creatorName = beadsCraftDto.getCreatorName();
        this.likeCount = beadsCraftDto.getLikeCount();
        this.isLiked = beadsCraftDto.getIsLiked();
        this.createdAt = beadsCraftDto.getCreatedAt();
        this.updateAt = beadsCraftDto.getUpdatedAt();
    }

}
