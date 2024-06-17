package ru.rsreu.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.rentall.entity.Category;
import ru.rsreu.rentall.entity.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
