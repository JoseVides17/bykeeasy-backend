package com.bykeeasy.application.port.in;

import java.math.BigDecimal;

public interface DriverStatsUseCase {
    DriverStats getDriverStats(String driverId);

    class DriverStats {
        private final BigDecimal dailyEarnings;
        private final int dailyJourneys;
        private final double rating;
        private final BigDecimal walletBalance;

        public DriverStats(BigDecimal dailyEarnings, int dailyJourneys, double rating, BigDecimal walletBalance) {
            this.dailyEarnings = dailyEarnings;
            this.dailyJourneys = dailyJourneys;
            this.rating = rating;
            this.walletBalance = walletBalance;
        }

        public BigDecimal getDailyEarnings() { return dailyEarnings; }
        public int getDailyJourneys() { return dailyJourneys; }
        public double getRating() { return rating; }
        public BigDecimal getWalletBalance() { return walletBalance; }
    }
}
