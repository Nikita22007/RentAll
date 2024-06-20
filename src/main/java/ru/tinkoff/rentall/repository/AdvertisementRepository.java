package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.rentall.entity.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

}
