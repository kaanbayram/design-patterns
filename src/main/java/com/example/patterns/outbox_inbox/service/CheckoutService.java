package com.example.patterns.outbox_inbox.service;

import com.example.patterns.errors.CheckoutException;
import com.example.patterns.errors.ErrorType;
import com.example.patterns.outbox_inbox.mapper.CheckoutMapper;
import com.example.patterns.outbox_inbox.mapper.OutboxCheckoutMapper;
import com.example.patterns.outbox_inbox.models.dto.CheckoutDto;
import com.example.patterns.outbox_inbox.models.entity.Checkout;
import com.example.patterns.outbox_inbox.repository.CheckoutRepository;
import com.example.patterns.outbox_inbox.repository.OutboxCheckoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckoutService {

    private final CheckoutMapper checkoutMapper;

    private final CheckoutRepository checkoutRepository;

    private final OutboxCheckoutRepository outboxCheckoutRepository;

    private final OutboxCheckoutMapper outboxCheckoutMapper;

    public CheckoutDto getCheckoutById(String id) {
        try {
            var checkout = checkoutRepository.findById(id).orElseThrow(() -> new CheckoutException(ErrorType.OUTBOX_GET_CHECKOUT_NOT_FOUND_EXCEPTION));
            log.info("Checkout found with checkoutId: {}", id);
            return checkoutMapper.toDto(checkout);
        } catch (Exception e) {
            log.error("Error occurred while getting checkout", e);
            throw new CheckoutException(ErrorType.OUTBOX_GET_CHECKOUT_EXCEPTION, e, id);
        }
    }

    @Transactional
    public void upsertCheckout(CheckoutDto checkout) {
        try {
            var saved = checkoutRepository.save(Checkout.builder()
                    .checkoutId(checkout.getCheckoutId())
                    .totalAmount(checkout.getTotalAmount())
                    .userId(checkout.getUserId())
                    .build());

            var outboxRecord = outboxCheckoutRepository.save(outboxCheckoutMapper.toOutboxCheckoutDto(saved));

            log.info("Checkout successfully upserted checkoutId: {} checkoutoutboxId: {}", saved.getId().toHexString(), outboxRecord.getId().toHexString());
        } catch (Exception e) {
            log.error("Error occurred while upserting checkout checkoutId: {}", checkout.getCheckoutId());
            throw new CheckoutException(ErrorType.OUTBOX_POST_CHECKOUT_UPSERT_EXCEPTION, e, checkout.getCheckoutId());
        }
    }
}
