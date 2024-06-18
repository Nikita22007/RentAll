package ru.rsreu.rentall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatDTO {
    private int chatId;
    private int userOneId;
    private int userTwoId;
    private String chatName;
}
