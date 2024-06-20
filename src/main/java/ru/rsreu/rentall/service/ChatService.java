package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.composite_id.ChatId;
import ru.rsreu.rentall.dto.ChatDTO;
import ru.rsreu.rentall.mapper.ChatMapper;
import ru.rsreu.rentall.repository.ChatRepository;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ChatDTO getChatById(int id, String userOneLogin, String userTwoLogin) {
        return ChatMapper.INSTANCE.toChatDTO(chatRepository.findById(new ChatId(id, userOneLogin, userTwoLogin)).orElse(null));
    }
}
