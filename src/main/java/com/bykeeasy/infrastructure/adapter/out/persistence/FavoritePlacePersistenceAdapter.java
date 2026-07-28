package com.bykeeasy.infrastructure.adapter.out.persistence;

import com.bykeeasy.application.port.out.FavoritePlaceRepositoryPort;
import com.bykeeasy.domain.model.FavoritePlace;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.FavoritePlaceEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataFavoritePlaceRepository;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FavoritePlacePersistenceAdapter implements FavoritePlaceRepositoryPort {

    private final SpringDataFavoritePlaceRepository favoritePlaceRepository;
    private final SpringDataUserRepository userRepository;

    @Override
    public FavoritePlace save(FavoritePlace favoritePlace) {
        UserEntity user = userRepository.findById(favoritePlace.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        FavoritePlaceEntity entity = PersistenceMapper.toEntity(favoritePlace);
        entity.setUser(user);
        
        FavoritePlaceEntity saved = favoritePlaceRepository.save(entity);
        return PersistenceMapper.toDomain(saved);
    }

    @Override
    public List<FavoritePlace> findByUserId(String userId) {
        return favoritePlaceRepository.findByUser_Id(userId).stream()
                .map(PersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        favoritePlaceRepository.deleteById(id);
    }

    @Override
    public Optional<FavoritePlace> findById(String id) {
        return favoritePlaceRepository.findById(id)
                .map(PersistenceMapper::toDomain);
    }
}
