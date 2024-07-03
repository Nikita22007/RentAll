package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.AdvertisementDTO;
import ru.tinkoff.rentall.entity.Advertisement;

@Mapper
public interface AdvertisementMapper {
    AdvertisementMapper INSTANCE = Mappers.getMapper(AdvertisementMapper.class);
    @Mapping(target = "userLogin", source = "advertisement.user.login")
    AdvertisementDTO toAdvertisementDTO(Advertisement advertisement);
    @Mapping(target = "user.login", source = "userLogin")
    Advertisement toAdvertisement(AdvertisementDTO advertisementDTO);
}
