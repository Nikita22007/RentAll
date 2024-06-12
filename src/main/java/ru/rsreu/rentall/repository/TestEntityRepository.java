package ru.rsreu.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rsreu.rentall.entity.TestEntity;

import java.util.Optional;

@Repository
public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
    Optional<TestEntity> findById(Long id);
}
