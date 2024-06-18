package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity(name = "adv_reviews")
@Getter @Setter
public class AdvertisementReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;
    private String userLogin;
    private int postId;
    private String feedback;
    private int mark;
    private Timestamp postTime;
}
