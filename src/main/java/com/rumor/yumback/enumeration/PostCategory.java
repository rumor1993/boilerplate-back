package com.rumor.yumback.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PostCategory {
    CHAT("잡담"), CRAFTS("완성작품"), MARKET("마켓");

    final private String name;

    PostCategory(String name) {
        this.name = name;
    }

    @JsonCreator
    public static PostCategory fromString(String key) {
        for(PostCategory action : PostCategory.values()) {
            if(action.name().equalsIgnoreCase(key)) {
                return action;
            }
        }
        return null;
    }

    @JsonValue
    public String getName() {
        return this.name;
    }
}
