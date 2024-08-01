package com.rumor.yumback.domains.oauth2.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOauth2User implements OAuth2User {
    private final Oauth2UserDto oauth2UserDto;

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> oauth2UserDto.role().toString());
        return collection;
    }

    @Override
    public String getName() {
        return oauth2UserDto.name();
    }

    public String getUsername() {
        return oauth2UserDto.username();
    }

    public String getPicture() {
        return oauth2UserDto.picture();
    }
}
