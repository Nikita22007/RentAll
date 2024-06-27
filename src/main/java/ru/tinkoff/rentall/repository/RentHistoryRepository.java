package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.rentall.entity.AdvertisementReview;
import ru.tinkoff.rentall.entity.RentHistory;
import ru.tinkoff.rentall.entity.UserReview;

import java.util.List;

public interface RentHistoryRepository extends JpaRepository<RentHistory, Integer> {
    List<RentHistory> findByUser_Login(String login);
    List<RentHistory> findByAdvertisement_AdvId(int advId);
}
