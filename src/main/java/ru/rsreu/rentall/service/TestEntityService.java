package ru.rsreu.rentall.service;

import ru.rsreu.rentall.entity.TestEntity;

import java.util.List;
import java.util.Optional;

public interface TestEntityService {
    List<TestEntity> getAll();

    Optional<TestEntity> findById(Long id);
}
