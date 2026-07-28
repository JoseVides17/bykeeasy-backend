package com.bykeeasy.application.port.in;

import com.bykeeasy.domain.model.Vehicle;
import java.io.InputStream;
import java.util.List;

public interface VehicleUseCase {
    Vehicle registerVehicle(String driverId, String licensePlate, String type, String model, String color, String brand, 
                            InputStream image, String imageName);
    Vehicle updateVehicle(String vehicleId, String licensePlate, String type, String model, String color, String brand, 
                          InputStream image, String imageName);
    List<Vehicle> getVehiclesByDriver(String driverId);
}
