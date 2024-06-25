package ru.tinkoff.rentall.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AdvertisementDTO {
    private String advName;
    private String description;
    private String timeUnit;
    private int rentTime;
    private boolean isBarterAllowed;
    private String advPrice;
    private String userLogin;
    private int imageId;
    private String categoryName;
    private LocalDateTime creationTime;
}
