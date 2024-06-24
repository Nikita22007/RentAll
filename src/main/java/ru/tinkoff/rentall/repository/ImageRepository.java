package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.rentall.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
