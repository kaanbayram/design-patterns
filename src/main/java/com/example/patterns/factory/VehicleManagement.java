package com.example.patterns.factory;

import com.example.patterns.factory.impl.ATV;
import com.example.patterns.factory.impl.Car;
import com.example.patterns.factory.impl.Motorcycle;
import com.example.patterns.factory.models.VehicleType;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VehicleManagement {

    public Vehicle vehicleFactory(VehicleType type) {
        return switch (type) {
            case VehicleType.CAR -> new Car(UUID.randomUUID().toString());
            case VehicleType.ATV -> new ATV(UUID.randomUUID().toString());
            case VehicleType.MOTORCYCLE -> new Motorcycle(UUID.randomUUID().toString());
        };
    }

    public void roadAssistance(VehicleType vehicleType) {
        vehicleFactory(vehicleType).callRepairService();
    }
}
