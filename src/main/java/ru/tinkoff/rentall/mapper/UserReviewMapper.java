package ru.tinkoff.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.rentall.dto.UserReviewDTO;
import ru.tinkoff.rentall.entity.UserReview;

@Mapper(uses = {UserMappingMethods.class})
public interface UserReviewMapper {
    UserReviewMapper INSTANCE = Mappers.getMapper(UserReviewMapper.class);

    @Mapping(target = "userLogin", source = "userLogin", qualifiedByName = "toLogin")
    @Mapping(target = "targetLogin", source = "targetLogin", qualifiedByName = "toLogin")
    UserReviewDTO toUserReviewDTO(UserReview userReview);

    @Mapping(target = "userLogin", source = "userLogin", qualifiedByName = "fromLogin")
    @Mapping(target = "targetLogin", source = "targetLogin", qualifiedByName = "fromLogin")
    UserReview toUserReview(UserReviewDTO userReviewDTO);
}
