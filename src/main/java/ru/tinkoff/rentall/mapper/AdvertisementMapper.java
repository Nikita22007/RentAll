package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.AdvertisementDTO;
import ru.tinkoff.rentall.entity.Advertisement;

@Mapper
public interface AdvertisementMapper {
    AdvertisementMapper INSTANCE = Mappers.getMapper(AdvertisementMapper.class);
    AdvertisementDTO toAdvertisementDTO(Advertisement advertisement);
    Advertisement toAdvertisement(AdvertisementDTO advertisementDTO);
}
