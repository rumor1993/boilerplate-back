package com.rumor.yumback.common.errors;

public record CustomErrorResponse(
        Integer code,
        String message
) {
}
