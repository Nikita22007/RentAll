package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.AdBoardDTO;
import ru.tinkoff.rentall.entity.Advertisement;

@Mapper
public interface AdBoardMapper {
    AdBoardMapper INSTANCE = Mappers.getMapper(AdBoardMapper.class);
    AdBoardDTO toAdBoardDTO(Advertisement advertisement);
    Advertisement toAdvertisement(AdBoardDTO adBoardDTO);
}
