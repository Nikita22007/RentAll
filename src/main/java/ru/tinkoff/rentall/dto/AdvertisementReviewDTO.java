package ru.tinkoff.rentall.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AdvertisementReviewDTO {
    private String userLogin;
    private int postId;
    private String feedback;
    private int mark;
    private LocalDateTime postTime;
}
