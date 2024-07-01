package ru.tinkoff.rentall.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Advertisements")
@Getter @Setter
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adv_id")
    private Integer advId;
    private String advName;
    private String description;
    private String timeUnit;
    private boolean isBarterAllowed;
    private String advPrice;
    @OneToOne @JoinColumn(name = "user_login", referencedColumnName = "login")
    private User user;
    private int imageId;
    private String categoryName;
    private LocalDateTime creationTime;
    private Boolean isRented;
}
