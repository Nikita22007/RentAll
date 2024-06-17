package ru.rsreu.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.rentall.entity.User;
import ru.rsreu.rentall.entity.UserReview;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {

}
