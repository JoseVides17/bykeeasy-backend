package com.bykeeasy.infrastructure.adapter.out.persistence.repository;

import com.bykeeasy.domain.model.JourneyStatus;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.JourneyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringDataJourneyRepository extends JpaRepository<JourneyEntity, String> {
    List<JourneyEntity> findByStatus(JourneyStatus status);

    @Query("SELECT j FROM JourneyEntity j WHERE j.driverId = :driverId AND j.status = :status AND j.createdAt >= :since")
    List<JourneyEntity> findDriverJourneysSince(
            @Param("driverId") String driverId, 
            @Param("status") JourneyStatus status, 
            @Param("since") LocalDateTime since
    );

    List<JourneyEntity> findByPassengerIdAndStatus(String passengerId, JourneyStatus status);
    
    List<JourneyEntity> findByDriverIdAndStatusIn(String driverId, java.util.Collection<JourneyStatus> statuses);
}
