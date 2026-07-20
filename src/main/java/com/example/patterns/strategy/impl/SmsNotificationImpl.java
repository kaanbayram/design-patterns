package com.example.patterns.strategy.impl;

import com.example.patterns.strategy.Notification;
import com.example.patterns.strategy.NotificationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SmsNotificationImpl implements Notification {
    @Override
    public void sendMessageToUser(String message) {
        System.out.printf("Sms message sent %s \n", message);
    }

    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }
}
