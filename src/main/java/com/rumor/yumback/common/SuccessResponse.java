package com.rumor.yumback.common;


import org.springframework.http.HttpStatus;

public record SuccessResponse(
        HttpStatus code,
        String message
) {
}
