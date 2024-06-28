package ru.tinkoff.rentall.entity;

import lombok.Getter;
import lombok.Setter;
import ru.tinkoff.rentall.composite_id.FavoritesId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity(name = "favorites")
@Getter @Setter
@IdClass(FavoritesId.class)
public class Favorites {
    @Id
    private Integer advId;
    @Id
    private String userLogin;
}
