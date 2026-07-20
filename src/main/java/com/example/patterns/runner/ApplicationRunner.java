package com.example.patterns.runner;

import com.example.patterns.decoder.service.BalanceService;
import com.example.patterns.decoder.service.PaymentMethod;
import com.example.patterns.decoder.service.PaymentMethodDecorator;
import com.example.patterns.decoder.service.impl.CreditCard;
import com.example.patterns.decoder.service.impl.FoodCard;
import com.example.patterns.strategy.service.NotificationService;
import com.example.patterns.strategy.service.NotificationType;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class ApplicationRunner {

    private final NotificationService notificationService;
    private final CreditCard creditCard;
    private final FoodCard foodCard;
    private final BalanceService balanceService;

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        // strategy
        notificationService.sendMessageToUser(NotificationType.SMS, "TestMessage");
        notificationService.sendMessageToUser(NotificationType.EMAIL, "TestMessage");
        notificationService.sendMessageToUser(NotificationType.SLACK, "TestMessage");

        // decorator
        PaymentMethod creditCardPayment = new PaymentMethodDecorator(creditCard, balanceService, BigDecimal.valueOf(250));
        creditCardPayment.pay();

        PaymentMethod foodCardPayment = new PaymentMethodDecorator(foodCard, balanceService, BigDecimal.valueOf(50));
        foodCardPayment.pay();

        //
    }
}
