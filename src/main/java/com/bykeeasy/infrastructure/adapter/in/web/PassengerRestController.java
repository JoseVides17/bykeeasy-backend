package com.bykeeasy.infrastructure.adapter.in.web;

import com.bykeeasy.application.port.in.PassengerStatsUseCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerRestController {

    private final PassengerStatsUseCase passengerStatsUseCase;

    @GetMapping("/{id}/stats")
    public ResponseEntity<PassengerStatsResponse> getStats(@PathVariable String id) {
        PassengerStatsUseCase.PassengerStats stats = passengerStatsUseCase.getPassengerStats(id);
        return ResponseEntity.ok(new PassengerStatsResponse(
                stats.getTotalSpent(),
                stats.getTotalJourneys(),
                stats.getRating()
        ));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PassengerStatsResponse {
        private BigDecimal totalSpent;
        private int totalJourneys;
        private double rating;
    }
}
