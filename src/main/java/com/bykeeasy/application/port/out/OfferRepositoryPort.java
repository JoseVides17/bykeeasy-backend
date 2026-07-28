package com.bykeeasy.application.port.out;

import com.bykeeasy.domain.model.Offer;
import java.util.List;
import java.util.Optional;

public interface OfferRepositoryPort {
    Offer save(Offer offer);
    Optional<Offer> findById(String id);
    List<Offer> findByJourneyId(String journeyId);
    void updateStatus(String offerId, String status);
}
