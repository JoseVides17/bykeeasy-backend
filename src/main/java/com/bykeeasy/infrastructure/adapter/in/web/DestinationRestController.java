package com.bykeeasy.infrastructure.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/destinations")
@RequiredArgsConstructor
public class DestinationRestController {

    private final com.bykeeasy.application.port.in.DestinationUseCase destinationUseCase;

    @GetMapping("/recent/{passengerId}")
    public ResponseEntity<List<RecentDestinationDto>> getRecentDestinations(@PathVariable String passengerId) {
        return ResponseEntity.ok(destinationUseCase.getRecentDestinations(passengerId));
    }
}
