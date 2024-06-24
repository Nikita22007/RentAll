package ru.tinkoff.rentall.entity;

import lombok.Getter;
import lombok.Setter;
import ru.tinkoff.rentall.composite_id.ChatId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity(name = "chats")
@Getter @Setter
@IdClass(ChatId.class)
public class Chat {
    @Id
    private Integer chatId;
    @Id
    private String userOneLogin;
    @Id
    private String userTwoLogin;
    private String chatName;
}
