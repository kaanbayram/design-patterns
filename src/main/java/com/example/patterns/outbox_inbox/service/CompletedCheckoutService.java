package com.example.patterns.outbox_inbox.service;

import com.example.patterns.outbox_inbox.models.entity.CheckoutProcessedEvent;
import com.example.patterns.outbox_inbox.models.event.CheckoutEvent;
import com.example.patterns.outbox_inbox.repository.CheckoutProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompletedCheckoutService {

    private final CheckoutProcessedEventRepository checkoutProcessedEventRepository;

    public void sendOutboxRecord(CheckoutEvent event) {
        try {
            checkoutProcessedEventRepository.insert(
                    CheckoutProcessedEvent.builder()
                            .id(new ObjectId(event.getCheckoutId()))
                            .build()
            );
            log.info("Checkout processed event saved: {}", event.getCheckoutId());
        } catch (DuplicateKeyException e) {
            log.warn("Event already processed, skipping: {}", event.getCheckoutId());
        }
    }
}
