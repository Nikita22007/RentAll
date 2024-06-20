package ru.tinkoff.rentall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatDTO {
    private int chatId;
    private String userOneLogin;
    private String userTwoLogin;
    private String chatName;
}
