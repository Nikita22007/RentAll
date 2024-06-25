package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.UserReviewDTO;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.entity.UserReview;
import ru.tinkoff.rentall.mapper.UserReviewMapper;
import ru.tinkoff.rentall.repository.UserRepository;
import ru.tinkoff.rentall.repository.UserReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserReviewService {
    private final UserReviewRepository userReviewRepository;
    private final UserRepository userRepository;

    public List<UserReviewDTO> getAll() {
        List<UserReview> userReviews = userReviewRepository.findAll();
        List<UserReviewDTO> userReviewDTOList = new ArrayList<>();
        for (UserReview userReview : userReviews) {
            userReviewDTOList.add(UserReviewMapper.INSTANCE.toUserReviewDTO(userReview));
        }
        return userReviewDTOList;
    }

    public UserReview createUserReview(UserReviewDTO userReviewDTO) {
        User userLogin = userRepository.findById(userReviewDTO.getUserLogin()).orElseThrow(() -> new RuntimeException());
        User targetLogin = userRepository.findById(userReviewDTO.getTargetLogin()).orElseThrow(() -> new RuntimeException());
        UserReview userReview = UserReviewMapper.INSTANCE.toUserReview(userReviewDTO);
        userReview.setUserLogin(userLogin);
        userReview.setTargetLogin(targetLogin);
        return userReviewRepository.save(userReview);
    }

    public Optional<UserReview> findById(Integer id) {
        return userReviewRepository.findById(id);
    }
}
