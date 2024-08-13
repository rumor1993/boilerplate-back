package com.rumor.yumback.common.errors;

import org.springframework.http.HttpStatus;

public record CustomErrorResponse(
        HttpStatus code,
        String message
) {
}
