package ru.rsreu.rentall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.rsreu.rentall.dto.ChatDTO;
import ru.rsreu.rentall.entity.Chat;
import ru.rsreu.rentall.mapper.ChatMapper;
import ru.rsreu.rentall.service.ChatService;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat_{id}")
    public ResponseEntity<ChatDTO> getChat(@PathVariable int id) {
        Chat chat = chatService.getChatById(id);
        if (chat != null) {
            return ResponseEntity.ok().body(ChatMapper.INSTANCE.toChatDTO(chat));
        }
        return ResponseEntity.status(500).build();
    }

}
