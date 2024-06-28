package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.repository.FavoritesRepository;

@Service
@RequiredArgsConstructor
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;
}
