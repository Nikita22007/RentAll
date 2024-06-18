package ru.rsreu.rentall.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
public class UserDTO {
    private String userFullName;
    private String userAddress;
    private String phoneNumber;
    private int login;
    private String userPassword;
    private Timestamp creationTime;
}
