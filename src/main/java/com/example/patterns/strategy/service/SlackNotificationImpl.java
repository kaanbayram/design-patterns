package com.example.patterns.strategy.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class SlackNotificationImpl implements Notification{
    @Override
    public Boolean sendMessageToUser(String message) {
        System.out.print("message sent");
        return true;
    }
}
