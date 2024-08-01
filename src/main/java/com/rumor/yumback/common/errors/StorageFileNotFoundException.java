package com.rumor.yumback.common.errors;

public class StorageFileNotFoundException extends RuntimeException {

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageFileNotFoundException(String message) {
        super(message);
    }
}
