package com.example.patterns.strategy.service.impl;

import com.example.patterns.strategy.service.Notification;
import com.example.patterns.strategy.service.NotificationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailNotificationImpl implements Notification {
    @Override
    public void sendMessageToUser(String message) {
        System.out.printf("Email message sent %s \n", message);
    }

    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }
}
