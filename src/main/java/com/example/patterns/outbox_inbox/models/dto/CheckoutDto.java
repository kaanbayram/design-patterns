package com.example.patterns.outbox_inbox.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckoutDto {
    private String checkoutId;
    private String userId;
    private BigDecimal totalAmount;
    private Instant completedAt;
}
