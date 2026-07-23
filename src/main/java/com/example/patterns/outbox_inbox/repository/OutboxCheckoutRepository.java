package com.example.patterns.outbox_inbox.repository;

import com.example.patterns.outbox_inbox.models.entity.CheckoutOutboxEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OutboxCheckoutRepository extends MongoRepository<CheckoutOutboxEvent,String> {
}
