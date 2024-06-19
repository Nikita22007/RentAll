package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.dto.MessageDTO;
import ru.rsreu.rentall.entity.Message;
import ru.rsreu.rentall.mapper.MessageMapper;
import ru.rsreu.rentall.repository.MessageRepository;

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
