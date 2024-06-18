package ru.rsreu.rentall.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class AdvertisementReviewDTO {
    private int userId;
    private int postId;
    private String feedback;
    private int mark;
    private Timestamp postTime;
}
