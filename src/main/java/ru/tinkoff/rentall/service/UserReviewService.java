package ru.tinkoff.rentall.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.entity.UserReview;
import ru.tinkoff.rentall.repository.UserReviewRepository;

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
