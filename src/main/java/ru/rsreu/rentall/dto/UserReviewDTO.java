package ru.rsreu.rentall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserReviewDTO {
    private int userId;
    private int targetId;
    private int feedback;
}
