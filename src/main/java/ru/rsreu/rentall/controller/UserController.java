package ru.rsreu.rentall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.rsreu.rentall.dto.LoginDTO;
import ru.rsreu.rentall.dto.UserDTO;
import ru.rsreu.rentall.service.UserService;

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
    public ResponseEntity<LoginDTO> login(@RequestBody LoginDTO loginDTO) {
        if (userService.logInUser(loginDTO) != null) {
            return ResponseEntity.status(200).body(loginDTO);
        }
        return ResponseEntity.status(400).build();
    }
}
