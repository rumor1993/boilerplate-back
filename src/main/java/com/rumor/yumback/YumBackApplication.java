package com.rumor.yumback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class YumBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(YumBackApplication.class, args);
    }
}
