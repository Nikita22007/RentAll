package ru.tinkoff.rentall.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class MessageDTO {
    private String senderLogin;
    private String receiverLogin;
    private String msg;
    private int imageId;
    private Timestamp sendingTime;
    private int chatId;
}
