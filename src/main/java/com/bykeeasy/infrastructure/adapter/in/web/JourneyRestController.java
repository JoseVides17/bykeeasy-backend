package com.bykeeasy.infrastructure.adapter.in.web;

import com.bykeeasy.domain.model.Coordinate;
import com.bykeeasy.domain.model.Journey;
import com.bykeeasy.application.port.in.RequestJourneyUseCase;
import com.bykeeasy.application.port.in.TakeJourneyUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/journeys")
public class JourneyRestController {

    private final RequestJourneyUseCase requestJourneyUseCase;
    private final TakeJourneyUseCase takeJourneyUseCase;

    public JourneyRestController(RequestJourneyUseCase requestJourneyUseCase, TakeJourneyUseCase takeJourneyUseCase) {
        this.requestJourneyUseCase = requestJourneyUseCase;
        this.takeJourneyUseCase = takeJourneyUseCase;
    }

    @PostMapping("/request")
    public ResponseEntity<Journey> requestJourney(@Valid @RequestBody JourneyDto journeyDto) {
        Journey journey = requestJourneyUseCase.requestJourney(
                journeyDto.getPassengerId(),
                journeyDto.getPassengerName(),
                new Coordinate(journeyDto.getLatOrigin(), journeyDto.getLonOrigin()),
                journeyDto.getOriginAddress(),
                new Coordinate(journeyDto.getLatDestination(), journeyDto.getLonDestination()),
                journeyDto.getDestinationAddress(),
                journeyDto.getFare()
        );
        return ResponseEntity.ok(journey);
    }

    @GetMapping("/available")
    public ResponseEntity<java.util.List<Journey>> getAvailableJourneys() {
        return ResponseEntity.ok(requestJourneyUseCase.getAvailableJourneys());
    }

    @PostMapping("/{id}/take")
    public ResponseEntity<Void> takeJourney(@PathVariable String id, @RequestParam String driverId) {
        takeJourneyUseCase.takeJourney(id, driverId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/start-route")
    public ResponseEntity<Void> startRoute(@PathVariable String id) {
        takeJourneyUseCase.startRouteToPickup(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/arrive")
    public ResponseEntity<Void> arrive(@PathVariable String id) {
        takeJourneyUseCase.arriveAtPickup(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startJourney(@PathVariable String id) {
        takeJourneyUseCase.startJourney(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> completeJourney(@PathVariable String id) {
        takeJourneyUseCase.completeJourney(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Journey> getJourney(@PathVariable String id) {
        return ResponseEntity.ok(requestJourneyUseCase.getJourneyById(id));
    }

    @GetMapping("/active/driver/{driverId}")
    public ResponseEntity<Journey> getActiveJourney(@PathVariable String driverId) {
        return requestJourneyUseCase.getActiveJourneyByDriverId(driverId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelJourney(@PathVariable String id) {
        takeJourneyUseCase.cancelJourney(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<java.util.List<Journey>> getHistory(@PathVariable String userId, @RequestParam com.bykeeasy.domain.model.UserRole role) {
        return ResponseEntity.ok(requestJourneyUseCase.getJourneyHistory(userId, role));
    }
}
