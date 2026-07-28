package com.bykeeasy.application.port.out;

import com.bykeeasy.domain.model.FavoritePlace;
import java.util.List;
import java.util.Optional;

public interface FavoritePlaceRepositoryPort {
    FavoritePlace save(FavoritePlace favoritePlace);
    List<FavoritePlace> findByUserId(String userId);
    void deleteById(String id);
    Optional<FavoritePlace> findById(String id);
}
