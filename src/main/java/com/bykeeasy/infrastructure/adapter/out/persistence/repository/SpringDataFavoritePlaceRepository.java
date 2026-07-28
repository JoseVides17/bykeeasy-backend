package com.bykeeasy.infrastructure.adapter.out.persistence.repository;

import com.bykeeasy.infrastructure.adapter.out.persistence.entity.FavoritePlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataFavoritePlaceRepository extends JpaRepository<FavoritePlaceEntity, String> {
    List<FavoritePlaceEntity> findByUser_Id(String userId);
}
