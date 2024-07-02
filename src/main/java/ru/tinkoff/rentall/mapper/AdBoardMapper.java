package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.AdBoardDTO;
import ru.tinkoff.rentall.entity.Advertisement;

@Mapper(uses = {UserMappingMethods.class})
public interface AdBoardMapper {
    AdBoardMapper INSTANCE = Mappers.getMapper(AdBoardMapper.class);
    @Mapping(target = "userLogin", source = "user", qualifiedByName = "toLogin")
    AdBoardDTO toAdBoardDTO(Advertisement advertisement);
    @Mapping(target = "user", source = "userLogin", qualifiedByName = "fromLogin")
    Advertisement toAdvertisement(AdBoardDTO adBoardDTO);
}
