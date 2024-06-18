package ru.rsreu.rentall.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class MessageDTO {
    private int senderId;
    private int getterId;
    private String msg;
    private int imageId;
    private Timestamp sendingTime;
    private int chatId;
}
