package ru.tinkoff.rentall.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MessageDTO {
    private String senderLogin;
    private String receiverLogin;
    private String msg;
    private int imageId;
    private LocalDateTime sendingTime;
    private int chatId;
}
