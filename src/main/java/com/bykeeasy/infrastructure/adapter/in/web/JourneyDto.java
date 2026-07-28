package com.bykeeasy.infrastructure.adapter.in.web;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class JourneyDto {
    private String id;
    @NotNull private String passengerId;
    private String passengerName;
    @NotNull private Double latOrigin;
    @NotNull private Double lonOrigin;
    private String originAddress;
    @NotNull private Double latDestination;
    @NotNull private Double lonDestination;
    private String destinationAddress;
    @NotNull private BigDecimal fare;
}
