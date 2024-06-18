package ru.rsreu.rentall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserReviewDTO {
    private String userLogin;
    private int targetId;
    private int feedback;
}
