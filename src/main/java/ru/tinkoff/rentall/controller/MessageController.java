package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.rentall.dto.MessageDTO;
import ru.tinkoff.rentall.service.MessageService;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/chat/message/send")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO) {
        messageService.send(messageDTO);
        return ResponseEntity.status(201).body(messageDTO);
    }

}
