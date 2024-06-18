package ru.rsreu.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.rsreu.rentall.dto.AdvertisementReviewDTO;
import ru.rsreu.rentall.entity.AdvertisementReview;

@Mapper
public interface AdvertisementReviewMapper {
    AdvertisementReviewMapper INSTANCE = Mappers.getMapper(AdvertisementReviewMapper.class);
    AdvertisementReviewDTO toAdvertisementReviewDTO(AdvertisementReview advertisementReview);
    AdvertisementReview toAdvertisementReview(AdvertisementReviewDTO advertisementReviewDTO);
}
