package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity(name = "Advertisements")
@Getter @Setter
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int advId;
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
