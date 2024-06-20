package ru.tinkoff.rentall.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.LoginDTO;
import ru.tinkoff.rentall.dto.UserDTO;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.UserMapper;
import ru.tinkoff.rentall.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toUser(userDTO);
        if (userRepository.findById(user.getLogin()).isEmpty()) {
            return userRepository.save(user);
        }
        return null;
    }

    public String logInUser(LoginDTO loginDTO) {
        Optional<User> userDB = userRepository.findById(loginDTO.getLogin());
        if (userDB.isPresent()) {
            if (loginDTO.getUserPassword().equals(userDB.get().getUserPassword())) {
                return userDB.get().getUserFullName();
            }
        }
        return null;
    }
}
