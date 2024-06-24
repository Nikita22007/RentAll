package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.repository.UserRepository;
import ru.tinkoff.rentall.security.LoginDetails;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public LoginDetails loadUserByUsername(String username) {
        User user = userRepository.findById(username).orElse(null);
        if (user != null) {
            return new LoginDetails(user);
        }
        return null;
    }

}
