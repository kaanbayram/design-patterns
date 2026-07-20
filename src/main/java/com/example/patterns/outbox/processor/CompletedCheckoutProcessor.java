package com.example.patterns.outbox.processor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@AllArgsConstructor
public class CompletedCheckoutProcessor extends Processor<Object> {

    private final ObjectMapper objectMapper;


    @Override
    public Object convertToObject() {

    }
}
