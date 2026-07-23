package com.example.patterns.outbox_inbox.mapper;

import com.example.patterns.outbox_inbox.utils.Constants;
import com.example.patterns.outbox_inbox.models.entity.Checkout;
import com.example.patterns.outbox_inbox.models.entity.CheckoutOutboxEvent;
import com.example.patterns.outbox_inbox.models.enums.CheckoutType;
import com.example.patterns.outbox_inbox.models.enums.OutboxStatus;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import tools.jackson.databind.ObjectMapper;

@Mapper(componentModel = "spring")
public interface OutboxCheckoutMapper {

    CheckoutOutboxEvent toOutboxCheckoutDto(Checkout checkout);

    @AfterMapping
    default void afterMapping(@MappingTarget CheckoutOutboxEvent event, Checkout checkout) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(checkout);
            event.setAggregateId(checkout.getCheckoutId());
            event.setEventType(CheckoutType.CHECKOUT_COMPLETED.getValue());
            event.setAggregateType(Constants.Checkout.OUTBOX_AGGREGATE_TYPE);
            event.setStatus(OutboxStatus.PENDING);
            event.setPayload(payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize checkout payload", e);
        }
    }
}
