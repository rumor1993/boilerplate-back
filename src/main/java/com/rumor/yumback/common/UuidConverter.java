package com.rumor.yumback.common;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidConverter {
    public static UUID fromBytes(byte[] bytes) {
        if (bytes.length != 16) {
            throw new IllegalArgumentException("Invalid UUID byte array length: " + bytes.length);
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
