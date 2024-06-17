package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.entity.UserReview;
import ru.rsreu.rentall.repository.UserReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserReviewService {
    private final UserReviewRepository userReviewRepository;

    public UserReviewService(UserReviewRepository userReviewRepositoryRepository) {
        this.userReviewRepository = userReviewRepositoryRepository;
    }

    public List<UserReview> getAll() {
        return userReviewRepository.findAll();
    }

    public Optional<UserReview> findById(Long id) {
        return userReviewRepository.findById(id);
    }
}
