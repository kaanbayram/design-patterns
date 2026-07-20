package com.example.patterns.factory.impl;

import com.example.patterns.factory.Vehicle;


public class ATV extends Vehicle {

    public ATV(String serialNumber) {
        super.serialNumber = serialNumber;
    }

    @Override
    public void callRepairService() {
        System.out.print("ATV repair service call \n");
    }
}
