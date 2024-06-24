package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.entity.UserReview;
import ru.tinkoff.rentall.repository.UserReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserReviewService {
    private final UserReviewRepository userReviewRepository;

    public List<UserReview> getAll() {
        return userReviewRepository.findAll();
    }

    public Optional<UserReview> findById(Integer id) {
        return userReviewRepository.findById(id);
    }
}
