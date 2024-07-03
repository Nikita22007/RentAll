package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.RentHistoryBoardDTO;
import ru.tinkoff.rentall.dto.RentHistoryDTO;
import ru.tinkoff.rentall.entity.Advertisement;
import ru.tinkoff.rentall.entity.RentHistory;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.RentHistoryBoardMapper;
import ru.tinkoff.rentall.mapper.RentHistoryMapper;
import ru.tinkoff.rentall.repository.AdvertisementRepository;
import ru.tinkoff.rentall.repository.RentHistoryRepository;
import ru.tinkoff.rentall.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentHistoryService {
    private final RentHistoryRepository rentHistoryRepository;
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;

    public RentHistory setRentHistory(RentHistoryDTO rentHistoryDTO) {
        User user = userRepository.findById(rentHistoryDTO.getLesseeLogin())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Advertisement advertisement = advertisementRepository.findById(rentHistoryDTO.getAdvId())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        RentHistory rentHistory = RentHistoryMapper.INSTANCE.toRentHistory(rentHistoryDTO);
        rentHistory.setUser(user);
        rentHistory.setAdvertisement(advertisement);
        return rentHistoryRepository.save(rentHistory);
    }

    public List<RentHistoryBoardDTO> getRentHistoryByUserLogin(String lesseeLogin) {
        List<RentHistory> rentHistories = rentHistoryRepository.findByUser_Login(lesseeLogin);
        return rentHistories
                .stream()
                .map(RentHistoryBoardMapper.INSTANCE::toRentHistoryBoardDTO)
                .collect(Collectors.toList());
    }

    public List<RentHistoryBoardDTO> getRentHistoriesByAdvertisementId(int advertisementId) {
        List<RentHistory> rentHistories = rentHistoryRepository.findByAdvertisement_AdvId(advertisementId);
        return rentHistories
                .stream()
                .map(RentHistoryBoardMapper.INSTANCE::toRentHistoryBoardDTO)
                .collect(Collectors.toList());
    }
}
