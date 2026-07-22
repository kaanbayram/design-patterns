package com.example.patterns.outbox.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Getter
@Setter
public class CheckoutDto {
    private String checkoutId;
    private String userId;
    private BigDecimal totalAmount;
    private Instant completedAt;
}
