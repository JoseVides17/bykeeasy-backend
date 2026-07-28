package com.bykeeasy.application.port.out;

import com.bykeeasy.domain.model.Passenger;
import java.util.Optional;

public interface PassengerRepositoryPort {
    Passenger save(Passenger passenger);
    Optional<Passenger> findById(String id);
    Optional<Passenger> findByEmail(String email);
}
