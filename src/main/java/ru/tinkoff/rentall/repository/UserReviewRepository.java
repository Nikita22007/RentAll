package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.rentall.entity.UserReview;

import java.util.List;

public interface UserReviewRepository extends JpaRepository<UserReview, Integer> {
    List<UserReview> findByTargetLogin_Login(String login);
    List<UserReview> findByUserLogin_Login(String login);

}
