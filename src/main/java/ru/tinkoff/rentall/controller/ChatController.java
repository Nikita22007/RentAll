package ru.tinkoff.rentall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.rentall.dto.ChatDTO;
import ru.tinkoff.rentall.service.ChatService;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/{id}")
    public ResponseEntity<ChatDTO> getChat(@PathVariable int id,
                                           @RequestParam String senderLogin,
                                           @RequestParam String receiverLogin) {
        ChatDTO chatDTO = chatService.getChatById(id, senderLogin, receiverLogin);
        if (chatDTO != null) {
            return ResponseEntity.ok().body(chatDTO);
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/create_chat")
    public ResponseEntity<ChatDTO> setChat(@RequestBody ChatDTO chatDTO) {
        if (chatService.createChat(chatDTO) != null){
            return ResponseEntity.status(201).body(chatDTO);
        }
        return ResponseEntity.status(400).build();
    }
}
