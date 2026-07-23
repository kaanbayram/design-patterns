package com.example.patterns.outbox_inbox.models.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@Document(collection = "checkouts")
public class Checkout extends BaseDocument {
    private String checkoutId;
    private String userId;
    private BigDecimal totalAmount;
}
