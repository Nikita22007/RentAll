package ru.rsreu.rentall.controller;

import org.springframework.web.bind.annotation.*;
import ru.rsreu.rentall.entity.AdvertisementReview;
import ru.rsreu.rentall.entity.Message;
import ru.rsreu.rentall.service.MessageService;

import java.util.*;

@RestController
public class MainController {
    private final MessageService messageService;

    public MainController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
    public List<Message> getTable() {
        return messageService.getAll();
    }

    @GetMapping("{id}")
    public Optional<Message> getById(@PathVariable Long id) {
        return messageService.findById(id);
    }

}
