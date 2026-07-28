package com.bykeeasy.infrastructure.adapter.in.web;

import com.bykeeasy.application.port.in.FavoritePlaceUseCase;
import com.bykeeasy.domain.model.FavoritePlace;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoritePlaceRestController {

    private final FavoritePlaceUseCase favoritePlaceUseCase;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoritePlace>> getFavorites(@PathVariable String userId) {
        return ResponseEntity.ok(favoritePlaceUseCase.getFavoritesByUser(userId));
    }

    @PostMapping
    public ResponseEntity<FavoritePlace> saveFavorite(@RequestBody FavoritePlace favoritePlace) {
        return ResponseEntity.ok(favoritePlaceUseCase.saveFavorite(favoritePlace));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable String id) {
        favoritePlaceUseCase.deleteFavorite(id);
        return ResponseEntity.ok().build();
    }
}
