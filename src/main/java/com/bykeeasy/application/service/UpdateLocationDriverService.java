package com.bykeeasy.application.service;

import com.bykeeasy.domain.model.Coordinate;
import com.bykeeasy.domain.model.Driver;
import com.bykeeasy.application.port.in.UpdateLocationDriverUseCase;
import com.bykeeasy.application.port.out.DriverRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class UpdateLocationDriverService implements UpdateLocationDriverUseCase {

    private final DriverRepositoryPort driverRepository;

    public UpdateLocationDriverService(DriverRepositoryPort driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    @Transactional
    public void updateLocation(String driverId, Coordinate location) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found: " + driverId));
        
        driver.setCurrentLocation(location);
        driverRepository.save(driver);
    }
}
