package ru.tinkoff.rentall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.rentall.dto.MessageDTO;
import ru.tinkoff.rentall.service.MessageService;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/chat/message/send")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO) {
        messageService.send(messageDTO);
        return ResponseEntity.status(201).body(messageDTO);
    }
}
