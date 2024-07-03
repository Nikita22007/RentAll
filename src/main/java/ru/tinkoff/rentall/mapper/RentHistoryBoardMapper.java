package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.RentHistoryBoardDTO;
import ru.tinkoff.rentall.entity.RentHistory;

@Mapper(componentModel = "spring")
public interface RentHistoryBoardMapper {
    RentHistoryBoardMapper INSTANCE = Mappers.getMapper(RentHistoryBoardMapper.class);
    @Mapping(target = "lesseeLogin", source = "rentHistory.user.login")
    @Mapping(target = "advId", source = "rentHistory.advertisement.advId")
    RentHistoryBoardDTO toRentHistoryBoardDTO(RentHistory rentHistory);
    RentHistory toRentHistory(RentHistoryBoardDTO rentHistoryBoardDTO);
}
