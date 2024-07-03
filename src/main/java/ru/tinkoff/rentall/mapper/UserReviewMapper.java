package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.UserReviewDTO;
import ru.tinkoff.rentall.entity.UserReview;

public interface UserReviewMapper {
    UserReviewMapper INSTANCE = Mappers.getMapper(UserReviewMapper.class);

    @Mapping(target = "userLogin", source = "userLogin.login")
    @Mapping(target = "targetLogin", source = "targetLogin.login")
    UserReviewDTO toUserReviewDTO(UserReview userReview);

    @Mapping(target = "userLogin.login", source = "userLogin")
    @Mapping(target = "targetLogin.login", source = "targetLogin")
    UserReview toUserReview(UserReviewDTO userReviewDTO);
}
