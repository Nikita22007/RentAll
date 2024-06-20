package ru.tinkoff.rentall.entity;

import jakarta.persistence.*;
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
    private String timeUnit;
    private int rentTime;
    private boolean isBarterAllowed;
    private String advPrice;
    @OneToOne @JoinColumn(name = "user_login", referencedColumnName = "login")
    private User user;
    private int imageId;
    private int categoryId;
    private Timestamp creationTime;
}
