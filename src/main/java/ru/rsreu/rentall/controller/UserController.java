package ru.rsreu.rentall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.rentall.entity.User;
import ru.rsreu.rentall.service.UserService;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService messageService) {
        this.userService = messageService;
    }

    @PostMapping("/registrate_user")
    public ResponseEntity<Void> setUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.status(201).build();
    }

}
