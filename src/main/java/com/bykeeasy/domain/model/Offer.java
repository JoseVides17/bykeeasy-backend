package com.bykeeasy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    private String id;
    private String journeyId;
    private String driverId;
    private BigDecimal proposedFare;
    private OfferStatus status;
    private LocalDateTime createdAt;
}
