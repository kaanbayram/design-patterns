package com.example.patterns.decoder;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class PaymentMethodDecorator implements PaymentMethod {

    private final PaymentMethod paymentMethod;
    private final BalanceService balanceService;
    private final BigDecimal amount;

    public PaymentMethodDecorator(PaymentMethod paymentMethod, BalanceService balanceService, BigDecimal amount) {
        this.paymentMethod = paymentMethod;
        this.balanceService = balanceService;
        this.amount = amount;
    }

    @Override
    public void pay() {
        if (!balanceService.balanceCheck(amount.toBigInteger())) {
            throw new RuntimeException("Insufficient balance for amount: " + amount);
        }
        log.info("Payment started: {} - amount: {}", paymentMethod.getClass().getSimpleName(), amount);
        paymentMethod.pay();
        log.info("Payment completed: {}", paymentMethod.getClass().getSimpleName());
    }
}
