package com.bykeeasy.infrastructure.adapter.in.web;

import com.bykeeasy.application.port.in.DriverStatsUseCase;
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
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverRestController {

    private final DriverStatsUseCase driverStatsUseCase;

    @GetMapping("/{id}/stats")
    public ResponseEntity<DriverStatsResponse> getStats(@PathVariable String id) {
        DriverStatsUseCase.DriverStats stats = driverStatsUseCase.getDriverStats(id);
        return ResponseEntity.ok(new DriverStatsResponse(
                stats.getDailyEarnings(),
                stats.getDailyJourneys(),
                stats.getRating(),
                stats.getWalletBalance()
        ));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DriverStatsResponse {
        private BigDecimal dailyEarnings;
        private int dailyJourneys;
        private double rating;
        private BigDecimal walletBalance;
    }
}
