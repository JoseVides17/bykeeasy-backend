package com.bykeeasy.infrastructure.adapter.in.web;

import com.bykeeasy.application.port.in.NegotiationUseCase;
import com.bykeeasy.domain.model.Offer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/negotiations")
@RequiredArgsConstructor
public class NegotiationRestController {

    private final NegotiationUseCase negotiationUseCase;

    @PostMapping("/offers")
    public ResponseEntity<Offer> createOffer(@RequestBody OfferRequest request) {
        Offer offer = negotiationUseCase.createOffer(
                request.getJourneyId(),
                request.getDriverId(),
                request.getProposedFare()
        );
        return ResponseEntity.ok(offer);
    }

    @GetMapping("/offers/journey/{journeyId}")
    public ResponseEntity<List<Offer>> getOffersByJourney(@PathVariable String journeyId) {
        List<Offer> offers = negotiationUseCase.getOffersByJourney(journeyId);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/offers/{offerId}/accept")
    public ResponseEntity<Void> acceptOffer(@PathVariable String offerId) {
        negotiationUseCase.acceptOffer(offerId);
        return ResponseEntity.ok().build();
    }

    @Data
    public static class OfferRequest {
        private String journeyId;
        private String driverId;
        private BigDecimal proposedFare;
    }
}
