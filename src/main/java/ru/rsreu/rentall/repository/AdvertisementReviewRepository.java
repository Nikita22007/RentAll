package ru.rsreu.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.rentall.entity.Advertisement;
import ru.rsreu.rentall.entity.AdvertisementReview;

public interface AdvertisementReviewRepository extends JpaRepository<AdvertisementReview, Long> {

}
