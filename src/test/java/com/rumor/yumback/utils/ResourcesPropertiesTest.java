package com.rumor.yumback.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("local")
class ResourcesPropertiesTest {
    @Autowired
    ResourcesProperties resourcesProperties;

    @Test
    void name() {
        String path = resourcesProperties.getPath();
        Assertions.assertThat(path).isEqualTo("test");
    }
}