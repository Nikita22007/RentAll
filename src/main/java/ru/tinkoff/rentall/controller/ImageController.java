package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tinkoff.rentall.dto.ImageDTO;
import ru.tinkoff.rentall.entity.Image;
import ru.tinkoff.rentall.service.ImageService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping(value = "/image_upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Image image = new Image();
            image.setImgName(file.getBytes());
            imageService.save(image);
            return ResponseEntity.status(200).build();
        } catch (IOException e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/image_load/{id}")
    public ResponseEntity<byte[]> loadImage(@PathVariable int id) {
        ImageDTO imageDTO = imageService.load(id);
        if (imageDTO != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageDTO.getImgName().length);
            return new ResponseEntity<>(imageDTO.getImgName(), headers, HttpStatus.OK);
        }
        return ResponseEntity.status(400).build();
    }

}
