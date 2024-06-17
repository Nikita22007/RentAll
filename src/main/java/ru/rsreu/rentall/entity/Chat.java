package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "chats")
@Getter @Setter @NoArgsConstructor
public class Chat {
    @Id
    private int chatId;
    private int userOneId;
    private int userTwoId;
    private String chatName;
}
