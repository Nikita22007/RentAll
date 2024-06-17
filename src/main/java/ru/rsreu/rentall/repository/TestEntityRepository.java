package ru.rsreu.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rsreu.rentall.entity.TestEntity;

import java.util.List;
import java.util.Optional;

public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {

}
