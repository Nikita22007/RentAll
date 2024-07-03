package ru.tinkoff.rentall.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class RentHistoryBoardDTO {
    private int rentId;
    private int advId;
    private String lesseeLogin;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
