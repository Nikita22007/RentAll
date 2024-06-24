package ru.tinkoff.rentall.composite_id;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class ChatId implements Serializable {
    private Integer chatId;
    private String userOneLogin;
    private String userTwoLogin;
}
