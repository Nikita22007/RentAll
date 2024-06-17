package ru.rsreu.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.rentall.entity.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

}
