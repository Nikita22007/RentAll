package ru.tinkoff.rentall.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.MessageDTO;
import ru.tinkoff.rentall.entity.Message;
import ru.tinkoff.rentall.mapper.MessageMapper;
import ru.tinkoff.rentall.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message send(MessageDTO messageDTO) {
        Message message = MessageMapper.INSTANCE.toMessage(messageDTO);
        return messageRepository.save(message);
    }
}
