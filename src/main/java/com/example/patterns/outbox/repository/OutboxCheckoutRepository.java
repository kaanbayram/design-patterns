package com.example.patterns.outbox.repository;

import com.example.patterns.outbox.models.entity.CheckoutOutboxEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OutboxCheckoutRepository extends MongoRepository<CheckoutOutboxEvent,String> {
}
