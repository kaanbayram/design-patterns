package com.example.patterns;

import com.example.patterns.strategy.service.NotificationService;
import com.example.patterns.strategy.service.impl.SmsNotificationImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatternsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatternsApplication.class, args);
    }
}
