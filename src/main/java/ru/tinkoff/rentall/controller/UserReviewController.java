package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.rentall.dto.AdvertisementDTO;
import ru.tinkoff.rentall.dto.AdvertisementReviewDTO;
import ru.tinkoff.rentall.dto.UserReviewDTO;
import ru.tinkoff.rentall.service.UserReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserReviewController {

    private final UserReviewService userReviewService;

    @GetMapping("/user_review")
    public ResponseEntity<List<UserReviewDTO>> getUserReview() {
        List<UserReviewDTO> userReviewDTOList = userReviewService.getAll();
        return ResponseEntity.status(200).body(userReviewDTOList);
    }

    @GetMapping("/user_review/{targetLogin}")
    public ResponseEntity<List<UserReviewDTO>> getUserReviewsByTargetLogin(@PathVariable String targetLogin) {
        List<UserReviewDTO> userReviewDTOList = userReviewService.getUserReviewsByTargetLogin(targetLogin);
        if (!userReviewDTOList.isEmpty()) {
            return ResponseEntity.status(200).body(userReviewDTOList);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/user_review/user/{userLogin}")
    public ResponseEntity<List<UserReviewDTO>> getUserReviewsByUserLogin(@PathVariable String userLogin) {
        List<UserReviewDTO> userReviewDTOList = userReviewService.getUserReviewsByUserLogin(userLogin);
        if (!userReviewDTOList.isEmpty()) {
            return ResponseEntity.status(200).body(userReviewDTOList);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/user_review/target/{targetLogin}/average_feedback")
    public ResponseEntity<Double> getAverageFeedbackByTargetLogin(@PathVariable String targetLogin) {
        double averageFeedback = userReviewService.getAverageFeedbackByTargetLogin(targetLogin);
        return ResponseEntity.status(200).body(averageFeedback);
    }

    @PostMapping("/create_user_review")
    public ResponseEntity<Void> setUserReview(@RequestBody UserReviewDTO userReviewDTO) {
        userReviewService.createUserReview(userReviewDTO);
        return ResponseEntity.status(201).build();
    }

}
