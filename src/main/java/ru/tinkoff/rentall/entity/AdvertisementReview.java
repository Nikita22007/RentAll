package ru.tinkoff.rentall.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "adv_reviews")
@Getter @Setter
public class AdvertisementReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;
    @OneToOne @JoinColumn(name = "user_login", referencedColumnName = "login")
    private User user;
    @OneToOne @JoinColumn(name = "adv_id", referencedColumnName = "adv_id")
    private Advertisement advertisement;
    private String feedback;
    private int mark;
    private LocalDateTime postTime;
}
