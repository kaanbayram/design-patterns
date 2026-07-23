package com.example.patterns.outbox_inbox.repository;

import com.example.patterns.outbox_inbox.models.entity.Checkout;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CheckoutRepository extends MongoRepository<Checkout, String> {
}
