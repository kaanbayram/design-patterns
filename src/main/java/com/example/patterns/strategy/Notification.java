package com.example.patterns.strategy;

public interface Notification {
    void sendMessageToUser(String message);

    NotificationType getType();
}
