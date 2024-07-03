package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.FavoritesDTO;
import ru.tinkoff.rentall.entity.Favorites;
import ru.tinkoff.rentall.mapper.FavoritesMapper;
import ru.tinkoff.rentall.repository.FavoritesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;

    public void saveFavorite(FavoritesDTO favoritesDTO) {
        Favorites favorites = FavoritesMapper.INSTANCE.toFavorites(favoritesDTO);
        favoritesRepository.save(favorites);
    }

    public List<FavoritesDTO> getFavorites(String login) {
        List<Favorites> favorites = favoritesRepository.findByUserLogin(login);
        return favorites
                .stream()
                .map(FavoritesMapper.INSTANCE::toFavoritesDTO)
                .collect(Collectors.toList());
    }
}
