package ru.tinkoff.rentall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.rentall.dto.CategoryDTO;
import ru.tinkoff.rentall.service.CategoryService;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService messageService) {
        this.categoryService = messageService;
    }

    @PostMapping("/create_category")
    public ResponseEntity<Void> setAdvertisement(@RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(201).build();
    }
}
