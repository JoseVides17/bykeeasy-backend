package com.bykeeasy.application.port.in;

import java.math.BigDecimal;

public interface PassengerStatsUseCase {
    PassengerStats getPassengerStats(String passengerId);

    class PassengerStats {
        private final BigDecimal totalSpent;
        private final int totalJourneys;
        private final double rating;

        public PassengerStats(BigDecimal totalSpent, int totalJourneys, double rating) {
            this.totalSpent = totalSpent;
            this.totalJourneys = totalJourneys;
            this.rating = rating;
        }

        public BigDecimal getTotalSpent() { return totalSpent; }
        public int getTotalJourneys() { return totalJourneys; }
        public double getRating() { return rating; }
    }
}
