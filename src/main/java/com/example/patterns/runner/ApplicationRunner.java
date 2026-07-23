package com.example.patterns.runner;

import com.example.patterns.decorator.BalanceService;
import com.example.patterns.decorator.PaymentMethod;
import com.example.patterns.decorator.PaymentMethodDecorator;
import com.example.patterns.decorator.impl.CreditCard;
import com.example.patterns.decorator.impl.FoodCard;
import com.example.patterns.factory.VehicleManagement;
import com.example.patterns.factory.models.VehicleType;
import com.example.patterns.outbox_inbox.models.dto.CheckoutDto;
import com.example.patterns.outbox_inbox.service.CheckoutService;
import com.example.patterns.strategy.NotificationService;
import com.example.patterns.strategy.NotificationType;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class ApplicationRunner {

    private final NotificationService notificationService;
    private final CreditCard creditCard;
    private final FoodCard foodCard;
    private final BalanceService balanceService;
    private final VehicleManagement vehicleManagement;
    private final CheckoutService checkoutService;

    @Order(2)
    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        // strategy
        notificationService.sendMessageToUser(NotificationType.SMS, "TestMessage");
        notificationService.sendMessageToUser(NotificationType.EMAIL, "TestMessage");
        notificationService.sendMessageToUser(NotificationType.SLACK, "TestMessage");

        // decorator
        PaymentMethod creditCardPayment = new PaymentMethodDecorator(creditCard, balanceService, BigDecimal.valueOf(250));
        PaymentMethod foodCardPayment = new PaymentMethodDecorator(foodCard, balanceService, BigDecimal.valueOf(50));

        foodCardPayment.pay();
        creditCardPayment.pay();

        // factory pattern
        vehicleManagement.roadAssistance(VehicleType.MOTORCYCLE);


        //outbox
        runOutbox();
    }

    private void runOutbox() {
        CheckoutDto checkout = CheckoutDto.builder()
                .userId(new ObjectId().toHexString())
                .checkoutId(new ObjectId().toHexString())
                .totalAmount(BigDecimal.valueOf(Double.parseDouble("12.5")))
                .build();

        checkoutService.upsertCheckout(checkout);

        checkoutService.upsertCheckout(checkout);
        checkoutService.upsertCheckout(checkout);
    }
}
