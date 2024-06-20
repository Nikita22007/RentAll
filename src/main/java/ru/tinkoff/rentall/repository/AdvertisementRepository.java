package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.rentall.entity.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
//    @Query(value = "SELECT login FROM users u WHERE u.login = :userLogin", nativeQuery = true)
//    List<Advertisement> findUser(@Param("userLogin") String userLogin);
}
