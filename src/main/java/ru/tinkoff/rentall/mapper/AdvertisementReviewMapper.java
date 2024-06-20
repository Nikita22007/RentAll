package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.AdvertisementReviewDTO;
import ru.tinkoff.rentall.entity.AdvertisementReview;

@Mapper
public interface AdvertisementReviewMapper {
    AdvertisementReviewMapper INSTANCE = Mappers.getMapper(AdvertisementReviewMapper.class);
    AdvertisementReviewDTO toAdvertisementReviewDTO(AdvertisementReview advertisementReview);
    AdvertisementReview toAdvertisementReview(AdvertisementReviewDTO advertisementReviewDTO);
}
