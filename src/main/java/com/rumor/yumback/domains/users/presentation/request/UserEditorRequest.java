package com.rumor.yumback.domains.users.presentation.request;

import com.rumor.yumback.domains.users.application.UserEditorDto;
import jakarta.validation.constraints.NotBlank;

public record UserEditorRequest(
        @NotBlank(message = "이름을 입력해주세요.") String name,
        String picture
) {
    public UserEditorDto toDto() {
        return new UserEditorDto(name, picture);
    }
}
