package com.example.patterns.outbox.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CheckoutType {
    CHECKOUT_COMPLETED("CheckoutCompleted");

    private final String value;
}
