package com.example.patterns.strategy.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    SMS("sms"),
    EMAIL("email"),
    SLACK("slack");

    private final String value;
}
