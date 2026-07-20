package com.example.patterns.outbox.processor;

public abstract class Processor<T> {
    public abstract T convertToObject();
}
