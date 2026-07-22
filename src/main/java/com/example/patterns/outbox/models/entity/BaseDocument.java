package com.example.patterns.outbox.models.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
public abstract class BaseDocument {
    @Id
    private ObjectId id;
    private Instant createdAt;
    private Instant updatedAt;
}
