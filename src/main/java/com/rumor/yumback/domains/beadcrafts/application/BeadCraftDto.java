package com.rumor.yumback.domains.beadcrafts.application;

import java.time.LocalDateTime;

public interface BeadCraftDto {
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

