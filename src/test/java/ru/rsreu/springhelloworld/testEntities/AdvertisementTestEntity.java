package ru.rsreu.springhelloworld.testEntities;

import ru.tinkoff.rentall.dto.AdvertisementDTO;
import ru.tinkoff.rentall.entity.Advertisement;

import java.sql.Timestamp;

public class AdvertisementTestEntity {
    public static Advertisement getAdvertisement(){
        Advertisement adv = new Advertisement();
        adv.setAdvName("Advertisement");
        adv.setDescription("Expensive auto");
        adv.setTimeUnit("hour");
        adv.setRentTime(12);
        adv.setBarterAllowed(true);
        adv.setUser(UserTestEntity.getUser());
        adv.setAdvPrice("5000");
        adv.setImageId(1);
        adv.setCategoryId(3);
        adv.setCreationTime(new Timestamp(System.currentTimeMillis()));
        return adv;
    }
}
