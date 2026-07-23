package com.example.patterns.outbox_inbox.models.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckoutEvent {
    private String checkoutId;
    private String userId;
    private BigDecimal totalAmount;
    private Instant completedAt;
}
