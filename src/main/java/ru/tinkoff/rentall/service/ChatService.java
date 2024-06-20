package ru.tinkoff.rentall.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.composite_id.ChatId;
import ru.tinkoff.rentall.dto.ChatDTO;
import ru.tinkoff.rentall.entity.Chat;
import ru.tinkoff.rentall.mapper.ChatMapper;
import ru.tinkoff.rentall.repository.ChatRepository;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ChatDTO getChatById(int id, String userOneLogin, String userTwoLogin) {
        Chat chat = chatRepository.findById(new ChatId(id, userOneLogin, userTwoLogin)).orElse(null);
        return ChatMapper.INSTANCE.toChatDTO(chat);
    }
}
