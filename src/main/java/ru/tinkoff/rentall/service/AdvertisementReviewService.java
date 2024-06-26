package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.AdvertisementReviewDTO;
import ru.tinkoff.rentall.entity.Advertisement;
import ru.tinkoff.rentall.entity.AdvertisementReview;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.AdvertisementReviewMapper;
import ru.tinkoff.rentall.repository.AdvertisementRepository;
import ru.tinkoff.rentall.repository.AdvertisementReviewRepository;
import ru.tinkoff.rentall.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementReviewService {
    private final AdvertisementReviewRepository advertisementReviewRepository;
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;

    public AdvertisementReview createAdvertisementReview(AdvertisementReviewDTO advertisementReviewDTO) {
        User user = userRepository.findById(advertisementReviewDTO.getUserLogin())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Advertisement advertisement = advertisementRepository.findById(advertisementReviewDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        AdvertisementReview advertisementReview = AdvertisementReviewMapper.INSTANCE.toAdvertisementReview(advertisementReviewDTO);
        advertisementReview.setUser(user);
        advertisementReview.setAdvertisement(advertisement);
        return advertisementReviewRepository.save(advertisementReview);
    }

    public List<AdvertisementReviewDTO> getAll() {
        List<AdvertisementReview> advertisementReviews = advertisementReviewRepository.findAll();
        return advertisementReviews.stream()
                .map(AdvertisementReviewMapper.INSTANCE::toAdvertisementReviewDTO)
                .collect(Collectors.toList());
    }

    public Optional<AdvertisementReview> findById(Integer id) {
        return advertisementReviewRepository.findById(id);
    }

    public List<AdvertisementReviewDTO> getReviewsByAdvertisementId(int advertisementId) {
        List<AdvertisementReview> reviews = advertisementReviewRepository.findByAdvertisement_AdvId(advertisementId);
        return reviews.stream()
                .map(AdvertisementReviewMapper.INSTANCE::toAdvertisementReviewDTO)
                .collect(Collectors.toList());
    }

    public double getAverageMarkByAdvertisementId(int advertisementId) {
        List<AdvertisementReview> reviews = advertisementReviewRepository.findByAdvertisement_AdvId(advertisementId);
        double averageMark = reviews.stream()
                .mapToInt(AdvertisementReview::getMark)
                .average()
                .orElse(0.0);
        // Округление до двух знаков после запятой
        return Math.round(averageMark * 100.0) / 100.0;
    }

    public List<AdvertisementReviewDTO> getReviewsByUserLogin(String userLogin) {
        List<AdvertisementReview> reviews = advertisementReviewRepository.findByUser_Login(userLogin);
        return reviews.stream()
                .map(AdvertisementReviewMapper.INSTANCE::toAdvertisementReviewDTO)
                .collect(Collectors.toList());
    }
}
