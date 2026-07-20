package com.example.patterns.errors;

import org.springframework.http.HttpStatus;

public class BaseRuntimeException extends RuntimeException {
    private final Integer errorCode;
    private final String message;
    private final HttpStatus status;

    public BaseRuntimeException(ErrorType errorType, Exception e, Object... args) {
        var message = String.format(errorType.getMessage(), args);
        super(message, e);
        this.errorCode = errorType.getErrorCode();
        this.message = message;
        this.status = errorType.getStatus();
    }

    public BaseRuntimeException(ErrorType errorType, Object... args) {
        var message = String.format(errorType.getMessage(), args);
        super(message);
        this.errorCode = errorType.getErrorCode();
        this.message = message;
        this.status = errorType.getStatus();
    }
}
