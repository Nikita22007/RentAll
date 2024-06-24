package ru.tinkoff.rentall.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.service.UserService;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.loadUserByUsername(user.getLogin()) != null) {
            errors.rejectValue("username", "Такой пользователь существует");
        }
    }
}
