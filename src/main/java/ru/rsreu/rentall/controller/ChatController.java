package ru.rsreu.rentall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/chat/{id}")
    public ResponseEntity<ChatDTO> getChat(@PathVariable int id,
                                           @RequestParam String senderLogin,
                                           @RequestParam String receiverLogin) {
        Chat chat = chatService.getChatById(id, senderLogin, receiverLogin);
        if (chat != null) {
            return ResponseEntity.ok().body(ChatMapper.INSTANCE.toChatDTO(chat));
        }
        return ResponseEntity.status(400).build();
    }
}
