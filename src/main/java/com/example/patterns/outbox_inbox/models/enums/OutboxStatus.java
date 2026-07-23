package com.example.patterns.outbox_inbox.models.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OutboxStatus {
    PENDING("pending"), PUBLISHED("published");

    private final String value;
}
