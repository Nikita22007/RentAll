package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.rentall.dto.ChatDTO;
import ru.tinkoff.rentall.dto.MessageDTO;
import ru.tinkoff.rentall.service.ChatService;
import ru.tinkoff.rentall.service.MessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final MessageService messageService;

    @GetMapping("/chat/{id}")
    public ResponseEntity<List<MessageDTO>> getChat(@PathVariable int id) {
        ChatDTO chatDTO = chatService.getChatById(id);
        if (chatDTO != null) {
            List<MessageDTO> messages = messageService.getMessages(chatDTO);
            return ResponseEntity.ok().body(messages);
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/create_chat")
    public ResponseEntity<ChatDTO> setChat(@RequestBody ChatDTO chatDTO) {
        if (chatService.createChat(chatDTO) != null) {
            return ResponseEntity.status(201).body(chatDTO);
        }
        return ResponseEntity.status(400).build();
    }
}
