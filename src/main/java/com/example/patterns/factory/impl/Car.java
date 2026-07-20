package com.example.patterns.factory.impl;

import com.example.patterns.factory.Vehicle;

public class Car extends Vehicle {
    public Car(String serialNumber) {
        super.serialNumber = serialNumber;
    }

    @Override
    public void callRepairService() {
        System.out.printf("Car repair service call \n", this.serialNumber);
    }
}
