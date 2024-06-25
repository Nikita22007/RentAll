package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.rentall.dto.AdvertisementReviewDTO;
import ru.tinkoff.rentall.service.AdvertisementReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdvertisementReviewController {
    private final AdvertisementReviewService advertisementReviewService;

    @PostMapping("/create_advertisement_review")
    public ResponseEntity<Void> setAdvertisementReview(@RequestBody AdvertisementReviewDTO advertisementReviewDTO) {
        advertisementReviewService.createAdvertisementReview(advertisementReviewDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/advertisement_review")
    public ResponseEntity<List<AdvertisementReviewDTO>> getAdvertisementReview() {
        List<AdvertisementReviewDTO> advertisementReviewDTOList = advertisementReviewService.getAll();
        return ResponseEntity.status(200).body(advertisementReviewDTOList);
    }

}
