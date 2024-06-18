package ru.rsreu.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.rsreu.rentall.dto.MessageDTO;
import ru.rsreu.rentall.entity.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);
    MessageDTO toMessageDTO(Message message);
    Message toMessage(MessageDTO messageDTO);
}
