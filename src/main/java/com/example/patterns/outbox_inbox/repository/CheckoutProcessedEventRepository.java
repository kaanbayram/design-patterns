package com.example.patterns.outbox_inbox.repository;


import com.example.patterns.outbox_inbox.models.entity.CheckoutProcessedEvent;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CheckoutProcessedEventRepository extends MongoRepository<CheckoutProcessedEvent, ObjectId> {
}
