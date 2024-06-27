package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.rentall.dto.AdBoardDTO;
import ru.tinkoff.rentall.dto.AdvertisementDTO;
import ru.tinkoff.rentall.dto.SearchDTO;
import ru.tinkoff.rentall.service.AdvertisementService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @PostMapping("/create_advertisement")
    public ResponseEntity<Void> setAdvertisement(@RequestBody AdvertisementDTO advertisementDTO) {
        advertisementService.createAdvertisement(advertisementDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/advertisement/{adv_id}")
    public ResponseEntity<AdvertisementDTO> getAdvertisement(@PathVariable int adv_id) {
        AdvertisementDTO advertisementDTO = advertisementService.getAdvertisementById(adv_id);
        if (advertisementDTO != null) {
            return ResponseEntity.status(200).body(advertisementDTO);
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/advertisement_board")
    public ResponseEntity<List<AdBoardDTO>> getAdvertisementBoard() {
        List<AdBoardDTO> board = advertisementService.getAdvertisementBoard();
        if (board != null) {
            return ResponseEntity.status(200).body(board);
        }
        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/delete_advertisement/{adv_id}")
    public ResponseEntity<AdvertisementDTO> deleteAdvertisement(@PathVariable int adv_id){
        AdvertisementDTO advertisementDTO = advertisementService.getAdvertisementById(adv_id);
        if (advertisementDTO != null) {
            advertisementService.deleteAdvertisement(advertisementDTO);
            return ResponseEntity.status(200).body(advertisementDTO);
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/search_advertisement")
    public ResponseEntity<List<AdvertisementDTO>> search(@RequestBody SearchDTO searchQuery) {
        List<AdvertisementDTO> searchedAdvertisements = advertisementService.search(searchQuery);
        return ResponseEntity.status(200).body(searchedAdvertisements);
    }
}
