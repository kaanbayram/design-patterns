package com.example.patterns.strategy;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final List<Notification> notificationList;
    private Map<NotificationType, Notification> notificationStrategies;

    @PostConstruct
    private void init() {
        notificationStrategies = notificationList.stream().collect(Collectors.toMap(Notification::getType, n -> n));
    }

    public void sendMessageToUser(NotificationType type, String message) {
        try {
            notificationStrategies.get(type).sendMessageToUser(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
