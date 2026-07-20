package com.example.patterns.strategy.service;

public interface Notification {
    void sendMessageToUser(String message);

    NotificationType getType();
}
