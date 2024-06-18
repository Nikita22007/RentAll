package ru.rsreu.rentall.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class UserDTO {
    private String userFullName;
    private String userAddress;
    private String phoneNumber;
    private int login;
    private String userPassword;
    private Timestamp creationTime;
}
