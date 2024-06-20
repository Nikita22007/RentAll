package ru.tinkoff.rentall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.rentall.dto.LoginDTO;
import ru.tinkoff.rentall.dto.UserDTO;
import ru.tinkoff.rentall.service.UserService;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registrate_user")
    public ResponseEntity<Void> setUser(@RequestBody UserDTO userDTO) {
        if (userService.saveUser(userDTO) != null) {
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String fullName = userService.logInUser(loginDTO);
        if (fullName != null) {
            return ResponseEntity.status(200).body(fullName);
        }
        return ResponseEntity.status(400).build();
    }
}
