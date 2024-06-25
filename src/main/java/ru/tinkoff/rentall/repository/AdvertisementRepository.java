package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tinkoff.rentall.entity.Advertisement;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {
    @Query(value = "SELECT * FROM advertisements a WHERE a.adv_name LIKE CONCAT('%', :substring, '%')", nativeQuery = true)
    List<Advertisement> findBySubstring(@Param("substring") String substring);
}
