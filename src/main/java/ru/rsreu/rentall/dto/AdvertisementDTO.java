package ru.rsreu.rentall.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class AdvertisementDTO {
    private String advName;
    private String description;
    private int timeUnit;
    private int rentTime;
    private boolean isBarterAllowed;
    private String advPrice;
    private int userId;
    private int imageId;
    private int categoryId;
    private Timestamp creationTime;
}
