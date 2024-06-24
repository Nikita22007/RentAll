package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.entity.AdvertisementReview;
import ru.tinkoff.rentall.repository.AdvertisementReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertisementReviewService {
    private final AdvertisementReviewRepository advertisementReviewRepository;

    public List<AdvertisementReview> getAll() {
        return advertisementReviewRepository.findAll();
    }

    public Optional<AdvertisementReview> findById(Integer id) {
        return advertisementReviewRepository.findById(id);
    }
}
