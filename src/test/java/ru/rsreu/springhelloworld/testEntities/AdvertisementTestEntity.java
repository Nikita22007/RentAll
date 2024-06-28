package ru.rsreu.springhelloworld.testEntities;

import ru.tinkoff.rentall.dto.AdvertisementDTO;
import ru.tinkoff.rentall.entity.Advertisement;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AdvertisementTestEntity {
    public static Advertisement getAdvertisement(){
        Advertisement adv = new Advertisement();
        adv.setAdvName("Advertisement");
        adv.setDescription("Expensive auto");
        adv.setTimeUnit("hour");
        adv.setBarterAllowed(true);
        adv.setUser(UserTestEntity.getUser());
        adv.setAdvPrice("5000");
        adv.setImageId(1);
        adv.setCategoryName("Costumes");
        adv.setCreationTime(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        return adv;
    }
}
