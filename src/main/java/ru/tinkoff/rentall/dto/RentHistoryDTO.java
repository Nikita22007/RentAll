package ru.tinkoff.rentall.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class RentHistoryDTO {
    private int advId;
    private String lesseeLogin;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
