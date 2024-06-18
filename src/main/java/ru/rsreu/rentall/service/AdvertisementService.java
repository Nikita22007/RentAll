package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.dto.AdvertisementDTO;
import ru.rsreu.rentall.dto.UserDTO;
import ru.rsreu.rentall.entity.Advertisement;
import ru.rsreu.rentall.entity.Chat;
import ru.rsreu.rentall.entity.User;
import ru.rsreu.rentall.mapper.AdvertisementMapper;
import ru.rsreu.rentall.mapper.UserMapper;
import ru.rsreu.rentall.repository.AdvertisementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public Advertisement createAdvertisement(AdvertisementDTO advertisementDTO) {
        Advertisement advertisement = AdvertisementMapper.INSTANCE.toAdvertisement(advertisementDTO);
        return advertisementRepository.save(advertisement);
    }

    public Advertisement getAdvertisementById(int id) {
        return advertisementRepository.findById((long) id).orElse(null);
    }

    public void deleteAdvertisement(Advertisement advertisement) {
        advertisementRepository.delete(advertisement);
    }

    public List<Advertisement> getAll() {
        return advertisementRepository.findAll();
    }

    public Optional<Advertisement> findById(Long id) {
        return advertisementRepository.findById(id);
    }
}
