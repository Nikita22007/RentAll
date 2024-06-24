package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.rentall.entity.UserReview;

public interface UserReviewRepository extends JpaRepository<UserReview, Integer> {

}
