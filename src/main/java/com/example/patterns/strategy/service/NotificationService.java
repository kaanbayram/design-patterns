package com.example.patterns.strategy.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class NotificationService {

    private final Map<String,Notification> strategies;

    public Boolean sendMessageToUser(String type, String message) {
        try {
            strategies.get(type).sendMessageToUser("Test");
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
