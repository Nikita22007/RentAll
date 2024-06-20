package ru.tinkoff.rentall.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.AdvertisementDTO;
import ru.tinkoff.rentall.entity.Advertisement;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.AdvertisementMapper;
import ru.tinkoff.rentall.repository.AdvertisementRepository;
import ru.tinkoff.rentall.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;

    public AdvertisementService(AdvertisementRepository advertisementRepository, UserRepository userRepository) {
        this.advertisementRepository = advertisementRepository;
        this.userRepository = userRepository;
    }

    public Advertisement createAdvertisement(AdvertisementDTO advertisementDTO) {
        User user = userRepository.findById(advertisementDTO.getUserLogin()).orElseThrow(() -> new RuntimeException());
        Advertisement advertisement = AdvertisementMapper.INSTANCE.toAdvertisement(advertisementDTO);
        advertisement.setUser(user);
        return advertisementRepository.save(advertisement);
    }

    public AdvertisementDTO getAdvertisementById(int id) {
        return AdvertisementMapper.INSTANCE.toAdvertisementDTO(advertisementRepository.findById((long) id).orElse(null));
    }

    public void deleteAdvertisement(AdvertisementDTO advertisementDTO) {
        advertisementRepository.delete(AdvertisementMapper.INSTANCE.toAdvertisement(advertisementDTO));
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
