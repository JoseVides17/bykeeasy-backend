package com.bykeeasy.application.port.out;

import com.bykeeasy.domain.model.Vehicle;
import java.util.List;
import java.util.Optional;

public interface VehicleRepositoryPort {
    Vehicle save(Vehicle vehicle, String driverId);
    List<Vehicle> findByDriverId(String driverId);
    void deleteById(String id);
    Optional<Vehicle> findById(String id);
}
