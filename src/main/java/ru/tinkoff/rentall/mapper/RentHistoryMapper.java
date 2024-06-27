package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.RentHistoryDTO;
import ru.tinkoff.rentall.entity.RentHistory;

@Mapper
public interface RentHistoryMapper {
    RentHistoryMapper INSTANCE = Mappers.getMapper(RentHistoryMapper.class);
    @Mapping(target = "lesseeLogin", source = "rentHistory.user.login")
    @Mapping(target = "advId", source = "rentHistory.advertisement.advId")
    RentHistoryDTO toRentHistoryDTO(RentHistory rentHistory);
    RentHistory toRentHistory(RentHistoryDTO rentHistoryDTO);
}
