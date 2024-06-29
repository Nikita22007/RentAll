package ru.tinkoff.rentall.composite_id;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class FavoritesId implements Serializable {
    private Integer advId;
    private String userLogin;
}
