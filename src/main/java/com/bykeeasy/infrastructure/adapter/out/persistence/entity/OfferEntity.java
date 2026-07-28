package com.bykeeasy.infrastructure.adapter.out.persistence.entity;

import com.bykeeasy.domain.model.OfferStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "offers")
@Data
public class OfferEntity {
    @Id
    private String id;
    private String journeyId;
    private String driverId;
    private BigDecimal proposedFare;
    
    @Enumerated(EnumType.STRING)
    private OfferStatus status;
    
    private LocalDateTime createdAt;
}
