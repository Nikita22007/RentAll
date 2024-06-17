package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.entity.Advertisement;
import ru.rsreu.rentall.repository.AdvertisementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public List<Advertisement> getAll() {
        return advertisementRepository.findAll();
    }

    public Optional<Advertisement> findById(Long id) {
        return advertisementRepository.findById(id);
    }
}
