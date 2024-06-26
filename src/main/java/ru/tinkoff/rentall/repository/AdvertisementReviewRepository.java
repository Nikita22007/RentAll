package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.rentall.entity.AdvertisementReview;

import java.util.List;

public interface AdvertisementReviewRepository extends JpaRepository<AdvertisementReview, Integer> {
    List<AdvertisementReview> findByAdvertisement_AdvId(int advId);
    List<AdvertisementReview> findByUser_Login(String login);
}
