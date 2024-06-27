package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.rentall.dto.ChatDTO;
import ru.tinkoff.rentall.service.ChatService;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/chat/{id}")
    public ResponseEntity<ChatDTO> getChat(@PathVariable int id) {
        ChatDTO chatDTO = chatService.getChatById(id);
        if (chatDTO != null) {
            return ResponseEntity.ok().body(chatDTO);
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
