package com.example.patterns.outbox.repository;

import com.example.patterns.outbox.models.entity.Checkout;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CheckoutRepository extends MongoRepository<Checkout, String> {
}
