package ru.rsreu.springhelloworld.testEntities;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import ru.tinkoff.rentall.entity.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

public class UserTestEntity {
    public static User getUser(){
        User user = new User();
        user.setUserAddress("Ryazan");
        user.setUserPassword("12345");
        user.setUserFullName("Kirill");
        user.setLogin("buhanka");
        user.setPhoneNumber("89276548756");
        user.setCreationTime(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        return user;
    }
}
