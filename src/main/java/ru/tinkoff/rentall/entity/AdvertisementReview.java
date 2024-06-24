package ru.tinkoff.rentall.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "adv_reviews")
@Getter @Setter
public class AdvertisementReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;
    private String userLogin;
    private int postId;
    private String feedback;
    private int mark;
    private LocalDateTime postTime;
}
