package com.rumor.yumback.domains.beadsCrafts.presentation.request;

import com.rumor.yumback.enumeration.BeadCraftCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BeadsCraftRequest(
        @NotEmpty(message = "이름을 입력해주세요.") String name,
        @NotNull(message = "카테고리를 선택해주세요.") BeadCraftCategory category
) {
}
