package com.bykeeasy.application.port.in;

import com.bykeeasy.domain.model.Offer;
import java.math.BigDecimal;
import java.util.List;

public interface NegotiationUseCase {
    Offer createOffer(String journeyId, String driverId, BigDecimal proposedFare);
    List<Offer> getOffersByJourney(String journeyId);
    void acceptOffer(String offerId);
}
