package ru.rsreu.springhelloworld.testEntities;

import ru.tinkoff.rentall.entity.User;

import java.sql.Timestamp;

public class UserTestEntity {
    public static User getUser(){
        User user = new User();
        user.setUserAddress("Ryazan");
        user.setUserPassword("12345");
        user.setUserFullName("Kirill");
        user.setLogin("buhanka");
        user.setPhoneNumber("89276548756");
        user.setCreationTime(new Timestamp(System.currentTimeMillis()));
        return user;
    }
}
