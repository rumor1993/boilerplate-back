package com.rumor.yumback.domains.beadsCrafts.application;

import java.time.LocalDateTime;

public interface BeadsCraftDto {
    byte[] getBeadCraftId();
    String getBeadCraftName();
    String getBeadCraftCategory();
    String getBeadCraftPicture();
    String getCreatorName();
    Long getLikeCount();
    Boolean getIsLiked();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}

