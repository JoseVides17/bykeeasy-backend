package com.bykeeasy.application.port.in;

import com.bykeeasy.domain.model.FavoritePlace;
import java.util.List;

public interface FavoritePlaceUseCase {
    FavoritePlace saveFavorite(FavoritePlace favoritePlace);
    List<FavoritePlace> getFavoritesByUser(String userId);
    void deleteFavorite(String id);
}
