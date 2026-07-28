package com.bykeeasy.application.port.out;

import com.bykeeasy.domain.model.Journey;
import com.bykeeasy.domain.model.JourneyStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JourneyRepositoryPort {
    Journey save(Journey journey);
    Optional<Journey> findById(String id);
    List<Journey> findByStatus(JourneyStatus status);
    List<Journey> findDriverJourneysSince(String driverId, JourneyStatus status, LocalDateTime since);
    List<Journey> findByPassengerIdAndStatus(String passengerId, JourneyStatus status);
    List<Journey> findByDriverIdAndStatuses(String driverId, java.util.Collection<JourneyStatus> statuses);
}
