package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.rentall.dto.DocumentDTO;
import ru.tinkoff.rentall.service.DocumentService;

import java.io.IOException;

@RestController @RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/generate-documents")
    public ResponseEntity<byte[]> generateDocuments(@RequestBody DocumentDTO documentDTO) {
        try {
            byte[] documentBytes = documentService.generateDocuments(documentDTO);
            if (documentBytes.length > 0) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "document.docx");
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(documentBytes);
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(400).build();
        }
    }
}
