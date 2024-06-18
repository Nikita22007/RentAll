package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.dto.UserDTO;
import ru.rsreu.rentall.entity.User;
import ru.rsreu.rentall.mapper.UserMapper;
import ru.rsreu.rentall.repository.UserRepository;

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

    public User logInUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toUser(userDTO);
        Optional<User> userDB = userRepository.findById(user.getLogin());
        if (userDB.isPresent()) {
            if (user.getUserPassword().equals(userDB.get().getUserPassword())) {
                return user;
            }
        }
        return null;
    }
}
