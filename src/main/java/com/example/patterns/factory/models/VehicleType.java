package com.example.patterns.factory.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VehicleType {
    CAR("car"),
    ATV("atv"),
    MOTORCYCLE("motorcycle");

    private final String value;
}
