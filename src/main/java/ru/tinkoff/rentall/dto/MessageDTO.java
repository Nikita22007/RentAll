package ru.tinkoff.rentall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String senderLogin;
    private String receiverLogin;
    private String msg;
    private int imageId;
    private LocalDateTime sendingTime;
    private int chatId;
}
