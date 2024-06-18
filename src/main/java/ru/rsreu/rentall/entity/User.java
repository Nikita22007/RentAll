package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity(name = "users")
@Getter @Setter
public class User {
    private String userFullName;
    private String userAddress;
    private String phoneNumber;
    @Id
    private String login;
    private String userPassword;
    private Timestamp creationTime;
}
