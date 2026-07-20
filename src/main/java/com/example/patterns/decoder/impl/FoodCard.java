package com.example.patterns.decoder.impl;

import com.example.patterns.decoder.PaymentMethod;
import org.springframework.stereotype.Service;

@Service
public class FoodCard implements PaymentMethod {
    @Override
    public void pay() {
        // ...payment processes
        System.out.print("FoodCard payment process completed");
    }
}
