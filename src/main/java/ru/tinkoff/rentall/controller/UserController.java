package ru.tinkoff.rentall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.rentall.dto.LoginDTO;
import ru.tinkoff.rentall.dto.UserAuthDTO;
import ru.tinkoff.rentall.dto.UserDTO;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.UserMapper;
import ru.tinkoff.rentall.security.JWTUtil;
import ru.tinkoff.rentall.security.UserValidator;
import ru.tinkoff.rentall.service.AuthService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    private final UserValidator userValidator;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registrate_user")
    public ResponseEntity<UserAuthDTO> regUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        User user = UserMapper.INSTANCE.toUser(userDTO);
        userValidator.validate(user, bindingResult);
        authService.saveUser(userDTO);
        String token = jwtUtil.generateToken(user.getLogin());
        String login = userDTO.getLogin();
        String userFullName =  userDTO.getUserFullName();
        return ResponseEntity.status(200).body(new UserAuthDTO(token, login, userFullName));
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthDTO> login(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDTO.getLogin(),
                loginDTO.getUserPassword()
        );
        try {
            authenticationManager.authenticate(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(400).build();
        }
        String generatedToken = jwtUtil.generateToken(loginDTO.getLogin());
        String login = loginDTO.getLogin();
        String userFullName = authService.getUserFullName(loginDTO);
        return ResponseEntity.status(200).body(new UserAuthDTO(generatedToken, login, userFullName));
    }
}
