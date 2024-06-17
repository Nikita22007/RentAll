package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity(name = "adv_reviews")
@Getter @Setter @NoArgsConstructor
public class AdvertisementReview {
    @Id
    private int reviewId;
    private int userId;
    private int postId;
    private String feedback;
    private int mark;
    private Timestamp postTime;
}
