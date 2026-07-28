package com.bykeeasy.application.port.out;

import com.bykeeasy.domain.model.Driver;

import java.util.Optional;

public interface DriverRepositoryPort {
    Driver save(Driver driver);
    Optional<Driver> findById(String id);
    Optional<Driver> findByEmail(String email);
}
