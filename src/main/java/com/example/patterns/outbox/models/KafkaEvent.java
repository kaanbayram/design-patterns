package com.example.patterns.outbox.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class KafkaEvent {
    private String before;
    private String after;
    private String updateDescription;
    private Object source;
    @JsonProperty("op")
    private String operation;
}
