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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/generate-documents")
    public ResponseEntity<byte[]> generateDocuments(@RequestBody DocumentDTO documentDTO) {
        try {
            String outputName = documentService.generateDocuments(documentDTO);
            byte[] documentBytes = Files.readAllBytes(Paths.get(outputName));
            if ((documentBytes.length > 0) && (!outputName.equals("error"))) {
                String outputNameCleaned = outputName.replace("src/main/resources/documents/", "");
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                String encodedFileName = URLEncoder.encode(outputNameCleaned, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
                String contentDisposition = "attachment; filename=\"" + outputNameCleaned + "\"; filename*=UTF-8''" + encodedFileName;
                headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
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
