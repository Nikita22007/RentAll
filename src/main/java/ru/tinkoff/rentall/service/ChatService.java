package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.ChatDTO;
import ru.tinkoff.rentall.entity.Chat;
import ru.tinkoff.rentall.mapper.ChatMapper;
import ru.tinkoff.rentall.repository.ChatRepository;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public Chat createChat(ChatDTO chatDTO) {
        Chat chat = ChatMapper.INSTANCE.toChat(chatDTO);
        if (chatRepository.findById(chat.getChatId()).isEmpty()) {
            return chatRepository.save(chat);
        }
        return null;
    }

    public ChatDTO getChatById(int id) {
        Chat chat = chatRepository.findById(id).orElse(null);
        return ChatMapper.INSTANCE.toChatDTO(chat);
    }
}
