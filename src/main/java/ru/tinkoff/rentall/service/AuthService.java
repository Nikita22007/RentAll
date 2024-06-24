package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.UserDTO;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.mapper.UserMapper;
import ru.tinkoff.rentall.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toUser(userDTO);
        if (userRepository.findById(user.getLogin()).isEmpty()) {
            String encodedPassword = passwordEncoder.encode(user.getUserPassword());
            user.setUserPassword(encodedPassword);
            return userRepository.save(user);
        }
        return null;
    }
}
