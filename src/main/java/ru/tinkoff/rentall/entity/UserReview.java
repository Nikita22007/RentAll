package ru.tinkoff.rentall.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import ru.tinkoff.rentall.entity.User;

@Entity(name = "user_reviews")
@Getter @Setter
public class UserReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer revId;
    @OneToOne @JoinColumn(name = "user_login", referencedColumnName = "login")
    private User userLogin;
    @OneToOne @JoinColumn(name = "target_login", referencedColumnName = "login")
    private User targetLogin;
    private String feedback;
    private int mark;
}
