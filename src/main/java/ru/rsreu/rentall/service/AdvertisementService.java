package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.dto.AdvertisementDTO;
import ru.rsreu.rentall.entity.Advertisement;
import ru.rsreu.rentall.mapper.AdvertisementMapper;
import ru.rsreu.rentall.repository.AdvertisementRepository;

import java.util.ArrayList;
import java.util.List;

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

    public List<AdvertisementDTO> getAdvertisementBoard() {
        List<Advertisement> advertisementBoard = advertisementRepository.findAll();
        List<AdvertisementDTO> advertisementDTOList = new ArrayList<>();
        for (Advertisement advertisement : advertisementBoard) {
            advertisementDTOList.add(AdvertisementMapper.INSTANCE.toAdvertisementDTO(advertisement));
        }
        return advertisementDTOList;
    }
}
