package ru.rsreu.rentall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.rsreu.rentall.composite_id.ChatId;

@Entity(name = "chats")
@Getter @Setter
@IdClass(ChatId.class)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatId;
    @Id
    private String userOneLogin;
    @Id
    private String userTwoLogin;
    private String chatName;
}
