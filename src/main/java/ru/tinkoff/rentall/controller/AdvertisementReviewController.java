package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        if (advertisementReviewDTOList != null){
            return ResponseEntity.status(200).body(advertisementReviewDTOList);
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/advertisement_review/advertisement/{advertisementId}")
    public ResponseEntity<List<AdvertisementReviewDTO>> getReviewsByAdvertisementId(@PathVariable int advertisementId) {
        List<AdvertisementReviewDTO> reviews = advertisementReviewService.getReviewsByAdvertisementId(advertisementId);
        if (reviews != null){
            return ResponseEntity.status(200).body(reviews);
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/advertisement_review/advertisement/{advertisementId}/average_mark")
    public ResponseEntity<Double> getAverageMarkByAdvertisementId(@PathVariable int advertisementId) {
        double averageMark = advertisementReviewService.getAverageMarkByAdvertisementId(advertisementId);
        return ResponseEntity.status(200).body(averageMark);
    }

    @GetMapping("/advertisement_review/user/{userLogin}")
    public ResponseEntity<List<AdvertisementReviewDTO>> getReviewsByUserLogin(@PathVariable String userLogin) {
        List<AdvertisementReviewDTO> reviews = advertisementReviewService.getReviewsByUserLogin(userLogin);
        if (reviews != null){
            return ResponseEntity.status(200).body(reviews);
        }
        return ResponseEntity.status(400).build();
    }

}
