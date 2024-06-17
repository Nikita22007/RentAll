package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.entity.Image;
import ru.rsreu.rentall.entity.User;
import ru.rsreu.rentall.repository.ImageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }
}
