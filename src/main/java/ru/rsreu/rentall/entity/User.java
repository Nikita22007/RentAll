package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity(name = "users")
@Getter @Setter @NoArgsConstructor
public class User {
    @Id
    private int userId;
    private String userFullName;
    private String userAddress;
    private String phoneNumber;
    private int login;
    private String userPassword;
    private Timestamp creationTime;
}
