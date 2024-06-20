package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.ChatDTO;
import ru.tinkoff.rentall.entity.Chat;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);
    ChatDTO toChatDTO(Chat chat);
    Chat toChat(ChatDTO chatDTO);
}
