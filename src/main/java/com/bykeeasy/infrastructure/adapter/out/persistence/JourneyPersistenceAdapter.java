package com.bykeeasy.infrastructure.adapter.out.persistence;

import com.bykeeasy.domain.model.Journey;
import com.bykeeasy.domain.model.JourneyStatus;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.JourneyEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataJourneyRepository;
import com.bykeeasy.application.port.out.JourneyRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JourneyPersistenceAdapter implements JourneyRepositoryPort {

    private final SpringDataJourneyRepository journeyRepository;

    public JourneyPersistenceAdapter(SpringDataJourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    @Override
    public Journey save(Journey journey) {
        JourneyEntity journeyEntity = PersistenceMapper.toJourneyEntity(journey);
        
        // Correctly handle NEW vs UPDATE for Persistable interface
        if (journeyRepository.existsById(journey.getId())) {
            journeyEntity.setNew(false);
        } else {
            journeyEntity.setNew(true);
        }
        
        JourneyEntity saved = journeyRepository.save(journeyEntity);
        return PersistenceMapper.toJourneyDomain(saved);
    }

    @Override
    public Optional<Journey> findById(String id) {
        return journeyRepository.findById(id)
                .map(PersistenceMapper::toJourneyDomain);
    }

    @Override
    public List<Journey> findByStatus(JourneyStatus status) {
        return journeyRepository.findByStatus(status).stream()
                .map(PersistenceMapper::toJourneyDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Journey> findDriverJourneysSince(String driverId, JourneyStatus status, LocalDateTime since) {
        return journeyRepository.findDriverJourneysSince(driverId, status, since).stream()
                .map(PersistenceMapper::toJourneyDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Journey> findByPassengerIdAndStatus(String passengerId, JourneyStatus status) {
        return journeyRepository.findByPassengerIdAndStatus(passengerId, status).stream()
                .map(PersistenceMapper::toJourneyDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Journey> findByDriverIdAndStatuses(String driverId, java.util.Collection<JourneyStatus> statuses) {
        return journeyRepository.findByDriverIdAndStatusIn(driverId, statuses).stream()
                .map(PersistenceMapper::toJourneyDomain)
                .collect(Collectors.toList());
    }
}
