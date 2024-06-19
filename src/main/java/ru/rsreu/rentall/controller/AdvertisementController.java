package ru.rsreu.rentall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.rentall.dto.AdvertisementDTO;
import ru.rsreu.rentall.entity.Advertisement;
import ru.rsreu.rentall.mapper.AdvertisementMapper;
import ru.rsreu.rentall.service.AdvertisementService;

@RestController
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService messageService) {
        this.advertisementService = messageService;
    }

    @PostMapping("/create_advertisement")
    public ResponseEntity<Void> setAdvertisement(@RequestBody AdvertisementDTO advertisementDTO) {
        advertisementService.createAdvertisement(advertisementDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/advertisement_{adv_id}")
    public ResponseEntity<AdvertisementDTO> getAdvertisement(@PathVariable int adv_id) {
        Advertisement advertisement = advertisementService.getAdvertisementById(adv_id);
        if (advertisement != null){
            return ResponseEntity.status(200).body(AdvertisementMapper.INSTANCE.toAdvertisementDTO(advertisement));
        }
        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/delete_advertisement_{adv_id}")
    public ResponseEntity<AdvertisementDTO> deleteAdvertisement(@PathVariable int adv_id){
        Advertisement advertisement = advertisementService.getAdvertisementById(adv_id);
        if (advertisement != null){
            advertisementService.deleteAdvertisement(advertisement);
            return ResponseEntity.status(200).body(AdvertisementMapper.INSTANCE.toAdvertisementDTO(advertisement));
        }
        return ResponseEntity.status(400).build();
    }
}
