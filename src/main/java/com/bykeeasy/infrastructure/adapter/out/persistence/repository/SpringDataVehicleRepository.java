package com.bykeeasy.infrastructure.adapter.out.persistence.repository;

import com.bykeeasy.infrastructure.adapter.out.persistence.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataVehicleRepository extends JpaRepository<VehicleEntity, String> {
    List<VehicleEntity> findByDriver_UserId(String driverId);
}
