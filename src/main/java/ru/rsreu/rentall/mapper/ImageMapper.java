package ru.rsreu.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.rsreu.rentall.dto.ImageDTO;
import ru.rsreu.rentall.entity.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);
    ImageDTO toImageDTO(Image image);
    Image toImage(ImageDTO imageDTO);
}
