package com.bykeeasy.application.service;

import com.bykeeasy.application.port.in.DestinationUseCase;
import com.bykeeasy.application.port.out.JourneyRepositoryPort;
import com.bykeeasy.domain.model.Journey;
import com.bykeeasy.domain.model.JourneyStatus;
import com.bykeeasy.infrastructure.adapter.in.web.RecentDestinationDto;

import java.util.List;
import java.util.stream.Collectors;

public class DestinationService implements DestinationUseCase {

    private final JourneyRepositoryPort journeyRepository;

    public DestinationService(JourneyRepositoryPort journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    @Override
    public List<RecentDestinationDto> getRecentDestinations(String passengerId) {
        List<Journey> journeys = journeyRepository.findByPassengerIdAndStatus(passengerId, JourneyStatus.COMPLETED);
        
        return journeys.stream()
                .limit(5) // Solo los últimos 5
                .map(j -> new RecentDestinationDto(
                        j.getId(),
                        "Viaje finalizado",
                        "Tarifa: $" + j.getFare(),
                        "RECENT",
                        j.getDestination().getLatitude(),
                        j.getDestination().getLongitude()
                ))
                .collect(Collectors.toList());
    }
}
