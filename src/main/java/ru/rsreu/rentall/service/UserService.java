package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.dto.UserDTO;
import ru.rsreu.rentall.entity.User;
import ru.rsreu.rentall.mapper.UserMapper;
import ru.rsreu.rentall.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.userDTOtoUser(userDTO);
        return userRepository.save(user);
    }
}
