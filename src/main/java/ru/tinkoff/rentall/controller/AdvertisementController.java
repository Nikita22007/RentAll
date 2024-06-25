package ru.tinkoff.rentall.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import ru.tinkoff.rentall.dto.AdvertisementDTO;
import ru.tinkoff.rentall.exception.InternalServerErrorException;
import ru.tinkoff.rentall.service.AdvertisementService;

import java.util.List;

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

    @GetMapping("/advertisement/{adv_id}")
    public ResponseEntity<AdvertisementDTO> getAdvertisement(@PathVariable int adv_id) throws BadRequestException, InternalServerErrorException {
        AdvertisementDTO advertisementDTO = advertisementService.getAdvertisementById(adv_id);
        if (advertisementDTO != null) {
            return ResponseEntity.status(200).body(advertisementDTO);
        } else {
            throw new InternalServerErrorException("Service returned null for advertisement");
            //throw new BadRequestException("Advertisement not found");
        }
    }

    @GetMapping("/advertisement_board")
    public ResponseEntity<List<AdvertisementDTO>> getAdvertisementBoard() throws BadRequestException{
        List<AdvertisementDTO> advertisementDTOList = advertisementService.getAdvertisementBoard();
        if (advertisementDTOList != null) {
            return ResponseEntity.status(200).body(advertisementDTOList);
        } else {
            throw new BadRequestException("Advertisements not found");
        }
    }

    @DeleteMapping("/delete_advertisement/{adv_id}")
    public ResponseEntity<AdvertisementDTO> deleteAdvertisement(@PathVariable int adv_id) throws BadRequestException{
        AdvertisementDTO advertisementDTO = advertisementService.getAdvertisementById(adv_id);
        if (advertisementDTO != null) {
            advertisementService.deleteAdvertisement(advertisementDTO);
            return ResponseEntity.status(200).body(advertisementDTO);
        }else {
            throw new BadRequestException("Advertisement not found");
        }
    }
}
