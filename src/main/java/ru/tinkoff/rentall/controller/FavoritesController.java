package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.rentall.dto.FavoritesDTO;
import ru.tinkoff.rentall.service.FavoritesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoritesController {
    private final FavoritesService favoritesService;

    @PostMapping("/mark_favorite")
    public ResponseEntity<Void> mark(@RequestBody FavoritesDTO favoritesDTO) {
        favoritesService.saveFavorite(favoritesDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<FavoritesDTO>> getFavorites() {
        List<FavoritesDTO> favoritesDTOS = favoritesService.getFavorites();
        return ResponseEntity.status(200).body(favoritesDTOS);
    }
}
