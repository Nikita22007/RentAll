package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.entity.Chat;
import ru.rsreu.rentall.repository.ChatRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<Chat> getAll() {
        return chatRepository.findAll();
    }

    public Optional<Chat> findById(Long id) {
        return chatRepository.findById(id);
    }
}
