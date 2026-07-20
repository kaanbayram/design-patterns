package com.example.patterns;

import org.springframework.boot.SpringApplication;

public class DevApplication {

    public static void main(String[] args) {
        SpringApplication.from(PatternsApplication::main)
                .with(DevContainersConfiguration.class)
                .run(args);
    }
}
