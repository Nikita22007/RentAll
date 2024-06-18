package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.dto.UserDTO;
import ru.rsreu.rentall.entity.User;
import ru.rsreu.rentall.mapper.UserMapper;
import ru.rsreu.rentall.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toUser(userDTO);
        return userRepository.save(user);
    }
}
