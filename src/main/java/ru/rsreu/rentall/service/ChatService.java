package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.entity.Chat;
import ru.rsreu.rentall.repository.ChatRepository;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat getChatById(int id) {
        return chatRepository.findById((long) id).orElse(null);
    }
}
