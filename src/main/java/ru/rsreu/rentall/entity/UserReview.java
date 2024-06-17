package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity(name = "user_reviews")
@Getter @Setter @NoArgsConstructor
public class UserReview {
    @Id
    private int revId;
    private int userId;
    private int targetId;
    private int feedback;
}
