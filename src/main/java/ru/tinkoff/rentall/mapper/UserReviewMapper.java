package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.UserReviewDTO;
import ru.tinkoff.rentall.entity.UserReview;

@Mapper
public interface UserReviewMapper {
    UserReviewMapper INSTANCE = Mappers.getMapper(UserReviewMapper.class);
    UserReviewDTO toUserReviewDTO(UserReview userReview);
    UserReview toUserReview(UserReviewDTO userReviewDTO);
}
