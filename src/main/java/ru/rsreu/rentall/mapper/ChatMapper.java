package ru.rsreu.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.rsreu.rentall.dto.ChatDTO;
import ru.rsreu.rentall.entity.Chat;

@Mapper
public interface ChatMapper {
    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);
    ChatDTO toChatDTO(Chat chat);
    Chat toChat(ChatDTO chatDTO);
}
