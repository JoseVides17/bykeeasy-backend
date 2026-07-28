package com.bykeeasy.application.service;

import com.bykeeasy.application.port.in.PassengerStatsUseCase;
import com.bykeeasy.application.port.out.JourneyRepositoryPort;
import com.bykeeasy.application.port.out.PassengerRepositoryPort;
import com.bykeeasy.domain.model.Journey;
import com.bykeeasy.domain.model.JourneyStatus;

import java.math.BigDecimal;
import java.util.List;

public class PassengerStatsService implements PassengerStatsUseCase {

    private final JourneyRepositoryPort journeyRepository;
    private final PassengerRepositoryPort passengerRepository;

    public PassengerStatsService(JourneyRepositoryPort journeyRepository, PassengerRepositoryPort passengerRepository) {
        this.journeyRepository = journeyRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public PassengerStats getPassengerStats(String passengerId) {
        List<Journey> completed = journeyRepository.findByPassengerIdAndStatus(passengerId, JourneyStatus.COMPLETED);

        BigDecimal totalSpent = completed.stream()
                .map(Journey::getFare)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalJourneys = completed.size();

        double rating = passengerRepository.findById(passengerId)
                .map(p -> (double) p.getQualification())
                .orElse(0.0);

        return new PassengerStats(totalSpent, totalJourneys, rating);
    }
}
