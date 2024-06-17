package ru.rsreu.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.rentall.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
