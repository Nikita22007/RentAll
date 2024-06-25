package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/create_user_review")
    public ResponseEntity<Void> setUserReview(@RequestBody UserReviewDTO userReviewDTO) {
        userReviewService.createUserReview(userReviewDTO);
        return ResponseEntity.status(201).build();
    }
}
