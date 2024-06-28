package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tinkoff.rentall.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query(value = "SELECT img_id FROM images i WHERE img_id NOT IN (SELECT image_id FROM advertisements a)", nativeQuery = true)
    void deleteUnusedImages();
}
