package ru.tinkoff.rentall.mapper;

import org.mapstruct.Named;
import ru.tinkoff.rentall.entity.User;

public class UserMappingMethods {

    @Named("fromLogin")
    public User fromLogin(String login) {
        User user = new User();
        user.setLogin(login);
        return user;
    }

    @Named("toLogin")
    public String toLogin(User user) {
        return user.getLogin();
    }
}