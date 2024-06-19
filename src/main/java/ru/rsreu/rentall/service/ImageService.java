package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.dto.ImageDTO;
import ru.rsreu.rentall.entity.Image;
import ru.rsreu.rentall.mapper.ImageMapper;
import ru.rsreu.rentall.repository.ImageRepository;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void save(Image image) {
        imageRepository.save(image);
    }

    public ImageDTO load(int id) {
        Image searchedImage = imageRepository.findById((long) id).orElse(null);
        if (searchedImage != null) {
            return ImageMapper.INSTANCE.toImageDTO(searchedImage);
        }
        return null;
    }
}
