package com.rumor.yumback.domains.beadsCrafts.application;

import com.rumor.yumback.enumeration.BeadCraftCategory;
import org.springframework.web.multipart.MultipartFile;

public record BeadsCraftRegisterDto(
        String name,
        String link,
        String description,
        BeadCraftCategory category,
        MultipartFile file
) {
}
