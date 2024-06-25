package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.AdvertisementReviewDTO;
import ru.tinkoff.rentall.dto.CategoryDTO;
import ru.tinkoff.rentall.entity.Advertisement;
import ru.tinkoff.rentall.entity.AdvertisementReview;
import ru.tinkoff.rentall.entity.Category;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.AdvertisementReviewMapper;
import ru.tinkoff.rentall.mapper.CategoryMapper;
import ru.tinkoff.rentall.repository.AdvertisementRepository;
import ru.tinkoff.rentall.repository.AdvertisementReviewRepository;
import ru.tinkoff.rentall.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertisementReviewService {
    private final AdvertisementReviewRepository advertisementReviewRepository;
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;

    public AdvertisementReview createAdvertisementReview(AdvertisementReviewDTO advertisementReviewDTO) {
        User user = userRepository.findById(advertisementReviewDTO.getUserLogin()).orElseThrow(() -> new RuntimeException());
        Advertisement advertisement = advertisementRepository.findById(advertisementReviewDTO.getPostId()).orElseThrow(() -> new RuntimeException());
        AdvertisementReview advertisementReview = AdvertisementReviewMapper.INSTANCE.toAdvertisementReview(advertisementReviewDTO);
        advertisementReview.setUser(user);
        advertisementReview.setAdvertisement(advertisement);
        return advertisementReviewRepository.save(advertisementReview);
    }

    public List<AdvertisementReviewDTO> getAll() {
        List<AdvertisementReview> advertisementReviews = advertisementReviewRepository.findAll();
        List<AdvertisementReviewDTO> advertisementReviewDTOList = new ArrayList<>();
        for (AdvertisementReview advertisementReview : advertisementReviews) {
            advertisementReviewDTOList.add(AdvertisementReviewMapper.INSTANCE.toAdvertisementReviewDTO(advertisementReview));
        }
        return advertisementReviewDTOList;
    }

    public Optional<AdvertisementReview> findById(Integer id) {
        return advertisementReviewRepository.findById(id);
    }
}
