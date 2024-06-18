package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "user_reviews")
@Getter @Setter
public class UserReview {
    @Id
    private int revId;
    private int userId;
    private int targetId;
    private int feedback;
}
