package ru.tinkoff.rentall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserAuthDTO {
    private String token;
    private String userFullName;
}
