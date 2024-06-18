package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity(name = "messages")
@Getter @Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int msgId;
    private String senderLogin;
    private String receiverLogin;
    private String msg;
    private int imageId;
    private Timestamp sendingTime;
    private int chatId;
}
