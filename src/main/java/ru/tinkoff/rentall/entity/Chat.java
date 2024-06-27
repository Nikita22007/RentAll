package ru.tinkoff.rentall.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "chats")
@Getter @Setter
public class Chat {
    @Id
    private Integer chatId;
    private String userOneLogin;
    private String userTwoLogin;
    private String chatName;
}
