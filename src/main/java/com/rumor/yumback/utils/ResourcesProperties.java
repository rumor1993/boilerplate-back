package com.rumor.yumback.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "resources")
@RequiredArgsConstructor
public class ResourcesProperties {
    private final String path;
}
