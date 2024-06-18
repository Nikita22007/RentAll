package ru.rsreu.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.rsreu.rentall.dto.AdvertisementReviewDTO;
import ru.rsreu.rentall.dto.UserReviewDTO;
import ru.rsreu.rentall.entity.AdvertisementReview;
import ru.rsreu.rentall.entity.UserReview;

@Mapper
public interface UserReviewMapper {
    UserReviewMapper INSTANCE = Mappers.getMapper(UserReviewMapper.class);
    UserReviewDTO toUserReviewDTO(UserReview userReview);
    UserReview toUserReview(UserReviewDTO userReviewDTO);
}
