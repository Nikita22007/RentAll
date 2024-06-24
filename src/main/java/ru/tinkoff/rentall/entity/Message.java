package ru.tinkoff.rentall.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "messages")
@Getter @Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer msgId;
    private String senderLogin;
    private String receiverLogin;
    private String msg;
    private int imageId;
    private LocalDateTime sendingTime;
    private int chatId;
}
