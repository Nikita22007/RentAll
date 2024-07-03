package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.AdBoardDTO;
import ru.tinkoff.rentall.entity.Advertisement;

public interface AdBoardMapper {
    AdBoardMapper INSTANCE = Mappers.getMapper(AdBoardMapper.class);
    @Mapping(target = "userLogin", source = "user.login")
    AdBoardDTO toAdBoardDTO(Advertisement advertisement);
    @Mapping(target = "user.login", source = "userLogin")
    Advertisement toAdvertisement(AdBoardDTO adBoardDTO);
}
