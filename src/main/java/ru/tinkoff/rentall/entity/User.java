package ru.tinkoff.rentall.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "users")
@Getter @Setter
public class User {
    private String userFullName;
    private String userAddress;
    private String phoneNumber;
    @Id
    private String login;
    private String userPassword;
    private LocalDateTime creationTime;
}
