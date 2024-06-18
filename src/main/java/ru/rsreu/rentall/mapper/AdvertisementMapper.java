package ru.rsreu.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.rsreu.rentall.dto.AdvertisementDTO;
import ru.rsreu.rentall.entity.Advertisement;

@Mapper
public interface AdvertisementMapper {
    AdvertisementMapper INSTANCE = Mappers.getMapper(AdvertisementMapper.class);
    AdvertisementDTO toAdvertisementDTO(Advertisement advertisement);
    Advertisement toAdvertisement(AdvertisementDTO advertisementDTO);
}
