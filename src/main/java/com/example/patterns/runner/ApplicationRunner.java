package com.example.patterns.runner;

import com.example.patterns.decoder.BalanceService;
import com.example.patterns.decoder.PaymentMethod;
import com.example.patterns.decoder.PaymentMethodDecorator;
import com.example.patterns.decoder.impl.CreditCard;
import com.example.patterns.decoder.impl.FoodCard;
import com.example.patterns.factory.VehicleManagement;
import com.example.patterns.factory.models.VehicleType;
import com.example.patterns.strategy.NotificationService;
import com.example.patterns.strategy.NotificationType;
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
    private final VehicleManagement vehicleManagement;

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
    }
}
