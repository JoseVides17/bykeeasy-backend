package com.bykeeasy.infrastructure.adapter.in.web;

import com.bykeeasy.application.port.in.PlaceSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
public class PlaceRestController {

    private final PlaceSearchUseCase placeSearchUseCase;

    @GetMapping("/search")
    public ResponseEntity<List<PlaceDto>> search(@RequestParam String query) {
        return ResponseEntity.ok(placeSearchUseCase.searchPlaces(query));
    }
}
