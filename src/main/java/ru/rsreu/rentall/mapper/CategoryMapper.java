package ru.rsreu.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.rsreu.rentall.dto.AdvertisementDTO;
import ru.rsreu.rentall.dto.CategoryDTO;
import ru.rsreu.rentall.entity.Advertisement;
import ru.rsreu.rentall.entity.Category;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    CategoryDTO toCategoryDTO(Category category);
    Category toCategory(CategoryDTO categoryDTO);
}
