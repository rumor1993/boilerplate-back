package com.rumor.yumback.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PostCategory {
    KOREAN, CHINESE, JAPANESE, WESTERN, ARTICLE, POST, HOME;

    @JsonCreator
    public static PostCategory fromString(String key) {
        for(PostCategory action : PostCategory.values()) {
            if(action.name().equalsIgnoreCase(key)) {
                return action;
            }
        }
        return null;
    }
}
