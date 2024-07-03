package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.AdBoardDTO;
import ru.tinkoff.rentall.dto.AdvertisementDTO;
import ru.tinkoff.rentall.dto.SearchDTO;
import ru.tinkoff.rentall.entity.Advertisement;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.AdBoardMapper;
import ru.tinkoff.rentall.mapper.AdvertisementMapper;
import ru.tinkoff.rentall.repository.AdvertisementRepository;
import ru.tinkoff.rentall.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;

    public Advertisement createAdvertisement(AdvertisementDTO advertisementDTO) {
        User user = userRepository.findById(advertisementDTO.getUserLogin()).orElseThrow(() -> new RuntimeException());
        Advertisement advertisement = AdvertisementMapper.INSTANCE.toAdvertisement(advertisementDTO);
        advertisement.setUser(user);
        return advertisementRepository.save(advertisement);
    }

    public AdvertisementDTO getAdvertisementById(int id) {
        return AdvertisementMapper.INSTANCE.toAdvertisementDTO(advertisementRepository.findById(id).orElse(null));
    }

    public List<AdBoardDTO> getAllAdvertisementsByLogin(String userLogin) {
        List<Advertisement> reviews = advertisementRepository.findByUser_Login(userLogin);
        return reviews
                .stream()
                .map(AdBoardMapper.INSTANCE::toAdBoardDTO)
                .collect(Collectors.toList());
    }

    public void deleteAdvertisement(AdvertisementDTO advertisementDTO) {
        advertisementRepository.delete(AdvertisementMapper.INSTANCE.toAdvertisement(advertisementDTO));
    }

    public List<AdBoardDTO> getAdvertisementBoard() {
        List<Advertisement> advertisementBoard = advertisementRepository.findAll();
        return advertisementBoard
                .stream()
                .map(AdBoardMapper.INSTANCE::toAdBoardDTO)
                .collect(Collectors.toList());
    }

    public List<AdvertisementDTO> search(SearchDTO searchQuery) {
        List<Advertisement> advertisements = advertisementRepository.findBySubstring(searchQuery.getSubstring());
        return advertisements
                .stream()
                .map(AdvertisementMapper.INSTANCE::toAdvertisementDTO)
                .collect(Collectors.toList());
    }
}
