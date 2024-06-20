package ru.tinkoff.rentall.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.entity.AdvertisementReview;
import ru.tinkoff.rentall.repository.AdvertisementReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementReviewService {
    private final AdvertisementReviewRepository advertisementReviewRepository;

    public AdvertisementReviewService(AdvertisementReviewRepository advertisementReviewRepository) {
        this.advertisementReviewRepository = advertisementReviewRepository;
    }

    public List<AdvertisementReview> getAll() {
        return advertisementReviewRepository.findAll();
    }

    public Optional<AdvertisementReview> findById(Long id) {
        return advertisementReviewRepository.findById(id);
    }
}
