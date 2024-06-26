package ru.tinkoff.rentall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserReviewDTO {
    private String userLogin;
    private String targetLogin;
    private String feedback;
    private int mark;
}
