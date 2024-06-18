package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity(name = "messages")
@Getter @Setter
public class Message {
    @Id
    private int msgId;
    private int senderId;
    private int getterId;
    private String msg;
    private int imageId;
    private Timestamp sendingTime;
    private int chatId;
}
