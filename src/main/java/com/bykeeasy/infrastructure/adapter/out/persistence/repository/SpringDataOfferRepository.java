package com.bykeeasy.infrastructure.adapter.out.persistence.repository;

import com.bykeeasy.infrastructure.adapter.out.persistence.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataOfferRepository extends JpaRepository<OfferEntity, String> {
    List<OfferEntity> findByJourneyId(String journeyId);
}
