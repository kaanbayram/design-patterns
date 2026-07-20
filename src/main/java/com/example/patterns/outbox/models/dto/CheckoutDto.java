package com.example.patterns.outbox.models.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder

public record CheckoutDto(
        String checkoutId,
        String userId,
        BigDecimal totalAmount,
        Instant completedAt
) {
}
