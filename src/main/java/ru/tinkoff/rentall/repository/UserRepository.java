package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.rentall.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
