package com.example.patterns.strategy.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SmsNotificationImpl implements Notification {
    @Override
    public Boolean sendMessageToUser(String message) {
        System.out.print("message sent");
        return true;
    }
}
