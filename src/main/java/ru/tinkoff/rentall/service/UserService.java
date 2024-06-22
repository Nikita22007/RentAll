package ru.tinkoff.rentall.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.LoginDTO;
import ru.tinkoff.rentall.dto.UserDTO;
import ru.tinkoff.rentall.dto.UserFullNameDTO;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.UserMapper;
import ru.tinkoff.rentall.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toUser(userDTO);
        if (userRepository.findById(user.getLogin()).isEmpty()) {
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    public UserFullNameDTO logInUser(LoginDTO loginDTO) {
        User userDB = userRepository.findById(loginDTO.getLogin()).orElse(null);
        if (userDB != null) {
            if (loginDTO.getUserPassword().equals(userDB.getUserPassword())) {
                return new UserFullNameDTO(userDB.getUserFullName());
            }
        }
        return null;
    }

}
