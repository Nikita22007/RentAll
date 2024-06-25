package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.AdvertisementReviewDTO;
import ru.tinkoff.rentall.entity.AdvertisementReview;

@Mapper
public interface AdvertisementReviewMapper {
    AdvertisementReviewMapper INSTANCE = Mappers.getMapper(AdvertisementReviewMapper.class);
    @Mapping(target = "userLogin", source = "advertisementReview.user.login")
    @Mapping(target = "postId", source = "advertisementReview.advertisement.advId")
    AdvertisementReviewDTO toAdvertisementReviewDTO(AdvertisementReview advertisementReview);
    AdvertisementReview toAdvertisementReview(AdvertisementReviewDTO advertisementReviewDTO);
}
