package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.FavoritesDTO;
import ru.tinkoff.rentall.entity.Favorites;
import ru.tinkoff.rentall.mapper.FavoritesMapper;
import ru.tinkoff.rentall.repository.FavoritesRepository;

import java.util.ArrayList;
import java.util.List;

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
        List<FavoritesDTO> favoritesDTOS = new ArrayList<>();
        for (Favorites favorite : favorites) {
            favoritesDTOS.add(FavoritesMapper.INSTANCE.toFavoritesDTO(favorite));
        }
        return  favoritesDTOS;
    }
}
