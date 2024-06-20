package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.rentall.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
