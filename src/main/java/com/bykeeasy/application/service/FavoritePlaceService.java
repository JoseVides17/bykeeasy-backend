package com.bykeeasy.application.service;

import com.bykeeasy.application.port.in.FavoritePlaceUseCase;
import com.bykeeasy.application.port.out.FavoritePlaceRepositoryPort;
import com.bykeeasy.domain.model.FavoritePlace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class FavoritePlaceService implements FavoritePlaceUseCase {

    private final FavoritePlaceRepositoryPort favoritePlaceRepository;

    @Override
    public FavoritePlace saveFavorite(FavoritePlace favoritePlace) {
        if (favoritePlace.getId() == null || favoritePlace.getId().isEmpty()) {
            favoritePlace.setId(UUID.randomUUID().toString());
        }
        return favoritePlaceRepository.save(favoritePlace);
    }

    @Override
    public List<FavoritePlace> getFavoritesByUser(String userId) {
        return favoritePlaceRepository.findByUserId(userId);
    }

    @Override
    public void deleteFavorite(String id) {
        favoritePlaceRepository.deleteById(id);
    }
}
