package com.rumor.yumback.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "files")
@RequiredArgsConstructor
public class FilesProperties {
    private final String rootLocation;

    public Path getRootLocation() {
        return Paths.get(rootLocation);
    }
}
