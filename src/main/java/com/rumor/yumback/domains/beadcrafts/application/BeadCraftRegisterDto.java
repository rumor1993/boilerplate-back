package com.rumor.yumback.domains.beadcrafts.application;

import com.rumor.yumback.enumeration.BeadCraftCategory;
import org.springframework.web.multipart.MultipartFile;

public record BeadCraftRegisterDto(
        String name,
        BeadCraftCategory category,
        MultipartFile file
) {
}
