package com.example.patterns.errors;

public class CheckoutException extends BaseRuntimeException {
    public CheckoutException(ErrorType type, Exception e, Object... args) {
        super(type, e, args);
    }

    public CheckoutException(ErrorType type, Object... args) {
        super(type, args);
    }
}
