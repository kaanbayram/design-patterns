package com.example.patterns.outbox_inbox.models;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

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
