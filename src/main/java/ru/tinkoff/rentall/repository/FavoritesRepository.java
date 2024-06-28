package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.rentall.composite_id.FavoritesId;
import ru.tinkoff.rentall.entity.Favorites;

public interface FavoritesRepository extends JpaRepository<Favorites, FavoritesId> {

}
