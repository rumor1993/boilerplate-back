package com.rumor.yumback.common.errors;

public class OauthUserNotFoundException extends RuntimeException {

    public OauthUserNotFoundException(String message) {
        super(message);
    }

    public OauthUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
