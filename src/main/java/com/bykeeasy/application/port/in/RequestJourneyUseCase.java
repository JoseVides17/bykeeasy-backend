package com.bykeeasy.application.port.in;

import com.bykeeasy.domain.model.Coordinate;
import com.bykeeasy.domain.model.Journey;

import java.math.BigDecimal;
import java.util.List;

public interface RequestJourneyUseCase {
    Journey requestJourney(String passengerId, String passengerName, Coordinate origin, String originAddress, Coordinate destination, String destinationAddress, BigDecimal rate);
    List<Journey> getAvailableJourneys();
    Journey getJourneyById(String id);
    java.util.Optional<Journey> getActiveJourneyByDriverId(String driverId);
    List<Journey> getJourneyHistory(String userId, com.bykeeasy.domain.model.UserRole role);
}
