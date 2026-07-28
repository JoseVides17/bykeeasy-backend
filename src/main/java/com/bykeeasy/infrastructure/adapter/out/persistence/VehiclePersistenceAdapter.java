package com.bykeeasy.infrastructure.adapter.out.persistence;

import com.bykeeasy.application.port.out.VehicleRepositoryPort;
import com.bykeeasy.domain.model.Vehicle;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.DriverEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.VehicleEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataDriverRepository;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataVehicleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VehiclePersistenceAdapter implements VehicleRepositoryPort {

    private final SpringDataVehicleRepository vehicleRepository;
    private final SpringDataDriverRepository driverRepository;

    @Override
    public Vehicle save(Vehicle vehicle, String driverId) {
        DriverEntity driver = null;
        if (driverId != null) {
            driver = driverRepository.findById(driverId)
                    .orElseThrow(() -> new RuntimeException("Driver not found"));
        }
                
        VehicleEntity entity = PersistenceMapper.toEntity(vehicle);
        if (driver != null) {
            entity.setDriver(driver);
        } else {
            // If driverId is null, try to keep existing driver if updating
            vehicleRepository.findById(vehicle.getId()).ifPresent(existing -> {
                entity.setDriver(existing.getDriver());
            });
        }
        
        VehicleEntity saved = vehicleRepository.save(entity);
        return PersistenceMapper.toDomain(saved);
    }

    @Override
    public List<Vehicle> findByDriverId(String driverId) {
        return vehicleRepository.findByDriver_UserId(driverId).stream()
                .map(PersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        return vehicleRepository.findById(id).map(PersistenceMapper::toDomain);
    }
}
