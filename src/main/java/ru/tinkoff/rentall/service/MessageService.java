package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.ChatDTO;
import ru.tinkoff.rentall.dto.MessageDTO;
import ru.tinkoff.rentall.entity.Message;
import ru.tinkoff.rentall.mapper.MessageMapper;
import ru.tinkoff.rentall.repository.MessageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Message send(MessageDTO messageDTO) {
        Message message = MessageMapper.INSTANCE.toMessage(messageDTO);
        return messageRepository.save(message);
    }

    public List<MessageDTO> getMessages(ChatDTO chatDTO) {
        List<Message> messages = messageRepository.findByChatId(chatDTO.getChatId());
        return messages
                .stream()
                .map((MessageMapper.INSTANCE::toMessageDTO))
                .collect(Collectors.toList());
    }
}
