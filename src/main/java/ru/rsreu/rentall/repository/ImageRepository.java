package ru.rsreu.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.rentall.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
