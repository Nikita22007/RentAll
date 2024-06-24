package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.ImageDTO;
import ru.tinkoff.rentall.entity.Image;
import ru.tinkoff.rentall.mapper.ImageMapper;
import ru.tinkoff.rentall.repository.ImageRepository;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void save(Image image) {
        imageRepository.save(image);
    }

    public ImageDTO load(int id) {
        Image searchedImage = imageRepository.findById(id).orElse(null);
        if (searchedImage != null) {
            return ImageMapper.INSTANCE.toImageDTO(searchedImage);
        }
        return null;
    }
}
