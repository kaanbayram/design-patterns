package com.example.patterns.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //1000
    OUTBOX_GET_CHECKOUT_EXCEPTION("Error occurred while getting Checkout checkoutId: %s", 10001, HttpStatus.INTERNAL_SERVER_ERROR),
    OUTBOX_GET_CHECKOUT_NOT_FOUND_EXCEPTION("Checkout not found checkoutId: %s", 10002, HttpStatus.NOT_FOUND);

    private final String message;
    private final Integer errorCode;
    private final HttpStatus status;
}
