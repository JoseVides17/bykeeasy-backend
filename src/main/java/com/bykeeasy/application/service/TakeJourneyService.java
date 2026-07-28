package com.bykeeasy.application.service;

import com.bykeeasy.domain.exception.JourneyNotFoundException;
import com.bykeeasy.domain.model.Journey;
import com.bykeeasy.domain.model.JourneyStatus;
import com.bykeeasy.application.port.in.TakeJourneyUseCase;
import com.bykeeasy.application.port.in.WalletUseCase;
import com.bykeeasy.application.port.out.JourneyRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class TakeJourneyService implements TakeJourneyUseCase {

    private final JourneyRepositoryPort journeyRepository;
    private final WalletUseCase walletUseCase;

    public TakeJourneyService(JourneyRepositoryPort journeyRepository, WalletUseCase walletUseCase) {
        this.journeyRepository = journeyRepository;
        this.walletUseCase = walletUseCase;
    }

    @Override
    @Transactional
    public void takeJourney(String journeyId, String driverId) {
        Journey journey = journeyRepository.findById(journeyId)
                .orElseThrow(() -> new JourneyNotFoundException("Journey not found: " + journeyId));
        
        journey.assignDriver(driverId);
        journeyRepository.save(journey);
    }

    @Override
    @Transactional
    public void completeJourney(String journeyId) {
        Journey journey = journeyRepository.findById(journeyId)
                .orElseThrow(() -> new JourneyNotFoundException("Journey not found: " + journeyId));
        
        if (journey.getStatus() == JourneyStatus.ACCEPTED) {
            journey.startJourney();
        }
        
        journey.completeJourney();
        journeyRepository.save(journey);
        
        // Debit commission from driver's wallet (Soft debit - don't block completion)
        try {
            if (journey.getDriverId() != null && journey.getCommission() != null) {
                walletUseCase.debit(
                        journey.getDriverId(),
                        journey.getCommission(),
                        "Commission for journey " + journey.getId()
                );
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not debit commission for journey " + journey.getId() + ": " + e.getMessage());
            // We proceed anyway to let the driver continue working
        }
    }

    @Override
    @Transactional
    public void cancelJourney(String journeyId) {
        Journey journey = journeyRepository.findById(journeyId)
                .orElseThrow(() -> new JourneyNotFoundException("Journey not found: " + journeyId));
        
        journey.setStatus(JourneyStatus.CANCELLED);
        journeyRepository.save(journey);
    }
}
