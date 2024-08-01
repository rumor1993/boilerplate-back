package com.rumor.yumback.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BeadCraftCategory {
    ALL, NECKLACE, BRACELET, RING, KEY_RING, EARRING, ETC;

    @JsonCreator
    public static BeadCraftCategory fromString(String key) {
        for(BeadCraftCategory action : BeadCraftCategory.values()) {
            if(action.name().equalsIgnoreCase(key)) {
                return action;
            }
        }
        return null;
    }
}
