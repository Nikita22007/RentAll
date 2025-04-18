package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.rentall.dto.RentHistoryBoardDTO;
import ru.tinkoff.rentall.dto.RentHistoryDTO;
import ru.tinkoff.rentall.service.RentHistoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RentHistoryController {
    private final RentHistoryService rentHistoryService;

    @PostMapping("/set_rent")
    public ResponseEntity<Void> setAdvertisement(@RequestBody RentHistoryDTO rentHistoryDTO) {
        rentHistoryService.setRentHistory(rentHistoryDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/rent_history/user/{lesseeLogin}")
    public ResponseEntity<List<RentHistoryBoardDTO>> getRentHistoriesByUserLogin(@PathVariable String lesseeLogin) {
        List<RentHistoryBoardDTO> rentHistoryDTOList = rentHistoryService.getRentHistoryByUserLogin(lesseeLogin);
        if (!rentHistoryDTOList.isEmpty()) {
            return ResponseEntity.status(200).body(rentHistoryDTOList);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/rent_history/advertisement/{advId}")
    public ResponseEntity<List<RentHistoryBoardDTO>> getRentHistoriesByAdvertisementId(@PathVariable int advId) {
        List<RentHistoryBoardDTO> rentHistories = rentHistoryService.getRentHistoriesByAdvertisementId(advId);
        if (rentHistories != null){
            return ResponseEntity.status(200).body(rentHistories);
        }
        return ResponseEntity.status(404).build();
    }
}
