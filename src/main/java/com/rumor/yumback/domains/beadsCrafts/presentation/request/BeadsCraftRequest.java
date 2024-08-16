package com.rumor.yumback.domains.beadsCrafts.presentation.request;

import com.rumor.yumback.enumeration.BeadCraftCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record BeadsCraftRequest(
        @NotEmpty(message = "이름을 입력해주세요.") String name,
        @NotNull(message = "카테고리를 선택해주세요.") BeadCraftCategory category,
        @URL(message = "URL 형식으로 입력해주세요.") String link,
        String authorName,
        String description
) {
}
