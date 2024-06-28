package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.FavoritesDTO;
import ru.tinkoff.rentall.entity.Favorites;

@Mapper(componentModel = "spring")
public interface FavoritesMapper {
    FavoritesMapper INSTANCE = Mappers.getMapper(FavoritesMapper.class);
    FavoritesDTO toFavoritesDTO(Favorites favorites);
    Favorites toFavorites(FavoritesDTO favoritesDTO);
}
