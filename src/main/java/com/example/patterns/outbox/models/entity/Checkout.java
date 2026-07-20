package com.example.patterns.outbox.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Document(collection = "checkouts")
public class Checkout extends BaseDocument {
    private String checkoutId;
    private String userId;
    private BigDecimal totalAmount;
}
