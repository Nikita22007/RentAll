package ru.tinkoff.rentall.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UserDTO {
    private String userFullName;
    private String userAddress;
    private String phoneNumber;
    private String login;
    private String userPassword;
    private LocalDateTime creationTime;
}
