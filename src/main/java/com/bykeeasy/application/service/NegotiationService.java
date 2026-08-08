package com.bykeeasy.application.service;

import com.bykeeasy.application.port.in.NegotiationUseCase;
import com.bykeeasy.application.port.out.DriverRepositoryPort;
import com.bykeeasy.application.port.out.JourneyRepositoryPort;
import com.bykeeasy.application.port.out.OfferRepositoryPort;
import com.bykeeasy.domain.model.Journey;
import com.bykeeasy.domain.model.JourneyStatus;
import com.bykeeasy.domain.model.Offer;
import com.bykeeasy.domain.model.OfferStatus;
import com.bykeeasy.domain.model.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class NegotiationService implements NegotiationUseCase {

    private final OfferRepositoryPort offerRepository;
    private final JourneyRepositoryPort journeyRepository;
    private final DriverRepositoryPort driverRepository;

    public NegotiationService(OfferRepositoryPort offerRepository, JourneyRepositoryPort journeyRepository, DriverRepositoryPort driverRepository) {
        this.offerRepository = offerRepository;
        this.journeyRepository = journeyRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public Offer createOffer(String journeyId, String driverId, BigDecimal proposedFare) {
        Offer offer = new Offer(
                UUID.randomUUID().toString(),
                journeyId,
                driverId,
                proposedFare,
                OfferStatus.PENDING,
                LocalDateTime.now()
        );
        return offerRepository.save(offer);
    }

    @Override
    public List<Offer> getOffersByJourney(String journeyId) {
        return offerRepository.findByJourneyId(journeyId);
    }

    @Override
    public void acceptOffer(String offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        
        Journey journey = journeyRepository.findById(offer.getJourneyId())
                .orElseThrow(() -> new RuntimeException("Journey not found"));

        // Update offer status
        offer.setStatus(OfferStatus.ACCEPTED);
        offerRepository.save(offer);

        // Update journey with accepted driver and fare
        journey.setDriverId(offer.getDriverId());
        
        // Recuperar datos reales del conductor y vehículo para el historial
        driverRepository.findById(offer.getDriverId()).ifPresent(driver -> {
            journey.setDriverName(driver.getName());
            if (driver.getVehicles() != null && !driver.getVehicles().isEmpty()) {
                Vehicle v = driver.getVehicles().get(0);
                journey.setVehiclePlate(v.getLicensePlate());
                journey.setVehicleModel(v.getBrand() + " " + v.getModel());
            }
        });

        journey.setFare(offer.getProposedFare());
        journey.setStatus(JourneyStatus.ACCEPTED);
        journeyRepository.save(journey);
        
        // Reject other offers for the same journey
        List<Offer> otherOffers = offerRepository.findByJourneyId(journey.getId());
        for (Offer o : otherOffers) {
            if (!o.getId().equals(offerId)) {
                o.setStatus(OfferStatus.REJECTED);
                offerRepository.save(o);
            }
        }
    }
}
