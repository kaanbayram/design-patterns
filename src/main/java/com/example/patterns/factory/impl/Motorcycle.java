package com.example.patterns.factory.impl;

import com.example.patterns.factory.Vehicle;

public class Motorcycle extends Vehicle {

    public Motorcycle(String serialNumber) {
        super.serialNumber = serialNumber;
    }

    @Override
    public void callRepairService() {
        System.out.print("Motorcycle repair service call \n");
    }
}
