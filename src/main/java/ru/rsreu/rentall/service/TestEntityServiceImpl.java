package ru.rsreu.rentall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsreu.rentall.entity.TestEntity;
import ru.rsreu.rentall.repository.TestEntityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TestEntityServiceImpl implements TestEntityService {
    private final TestEntityRepository testEntityRepository;

    public TestEntityServiceImpl(TestEntityRepository testEntityRepository) {
        this.testEntityRepository = testEntityRepository;
    }

    @Override
    public List<TestEntity> getAll() {
        return testEntityRepository.findAll();
    }

    @Override
    public Optional<TestEntity> findById(Long id) {
        return testEntityRepository.findById(id);
    }
}
