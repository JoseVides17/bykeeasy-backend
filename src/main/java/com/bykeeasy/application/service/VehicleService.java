package com.bykeeasy.application.service;

import com.bykeeasy.application.port.in.VehicleUseCase;
import com.bykeeasy.application.port.out.FileStoragePort;
import com.bykeeasy.application.port.out.VehicleRepositoryPort;
import com.bykeeasy.domain.model.Vehicle;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class VehicleService implements VehicleUseCase {

    private final VehicleRepositoryPort vehicleRepository;
    private final FileStoragePort fileStorage;

    @Override
    public Vehicle registerVehicle(String driverId, String licensePlate, String type, String model, String color, String brand, 
                                   InputStream image, String imageName) {
        
        String imageUrl = null;
        if (image != null) {
            imageUrl = fileStorage.store(image, imageName, "vehicles");
        }

        Vehicle vehicle = new Vehicle(
                UUID.randomUUID().toString(),
                licensePlate,
                type,
                model,
                color,
                brand,
                imageUrl
        );

        return vehicleRepository.save(vehicle, driverId);
    }

    @Override
    public Vehicle updateVehicle(String vehicleId, String licensePlate, String type, String model, String color, String brand, 
                                 InputStream image, String imageName) {
        
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        if (licensePlate != null) vehicle.setLicensePlate(licensePlate);
        if (type != null) vehicle.setType(type);
        if (model != null) vehicle.setModel(model);
        if (color != null) vehicle.setColor(color);
        if (brand != null) vehicle.setBrand(brand);
        
        if (image != null) {
            vehicle.setImageUrl(fileStorage.store(image, imageName, "vehicles"));
        }

        return vehicleRepository.save(vehicle, null); // The Adapter should handle driver id if null but existing
    }

    @Override
    public List<Vehicle> getVehiclesByDriver(String driverId) {
        return vehicleRepository.findByDriverId(driverId);
    }
}
