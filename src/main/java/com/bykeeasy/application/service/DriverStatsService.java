package com.bykeeasy.application.service;

import com.bykeeasy.application.port.in.DriverStatsUseCase;
import com.bykeeasy.application.port.in.WalletUseCase;
import com.bykeeasy.application.port.out.DriverRepositoryPort;
import com.bykeeasy.application.port.out.JourneyRepositoryPort;
import com.bykeeasy.application.port.out.WalletRepositoryPort;
import com.bykeeasy.domain.model.Journey;
import com.bykeeasy.domain.model.JourneyStatus;
import com.bykeeasy.domain.model.Wallet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DriverStatsService implements DriverStatsUseCase {

    private final JourneyRepositoryPort journeyRepository;
    private final DriverRepositoryPort driverRepository;
    private final WalletUseCase walletUseCase;

    public DriverStatsService(JourneyRepositoryPort journeyRepository, DriverRepositoryPort driverRepository, WalletUseCase walletUseCase) {
        this.journeyRepository = journeyRepository;
        this.driverRepository = driverRepository;
        this.walletUseCase = walletUseCase;
    }

    @Override
    public DriverStats getDriverStats(String driverId) {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        
        List<Journey> completedToday = journeyRepository.findDriverJourneysSince(
                driverId, 
                JourneyStatus.COMPLETED, 
                startOfToday
        );

        BigDecimal earnings = completedToday.stream()
                .map(Journey::getFare)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int journeyCount = completedToday.size();

        double rating = driverRepository.findById(driverId)
                .map(d -> (double) d.getQualification())
                .orElse(0.0);

        BigDecimal walletBalance = walletUseCase.getWalletByUserId(driverId).getBalance();

        return new DriverStats(earnings, journeyCount, rating, walletBalance);
    }
}
