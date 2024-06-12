package ru.rsreu.rentall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.rentall.entity.TestEntity;
import ru.rsreu.rentall.repository.TestEntityRepository;
import ru.rsreu.rentall.service.TestEntityService;
import ru.rsreu.rentall.service.TestEntityServiceImpl;

import java.util.*;

@RestController
public class MainController {
    @Autowired
    private TestEntityService testEntityService;
    @GetMapping("/")
    public List<TestEntity> getTable() {
        return testEntityService.getAll();
    }

    @GetMapping("{id}")
    public Optional<TestEntity> getById(@PathVariable Long id) {
        return testEntityService.findById(id);
    }

}
