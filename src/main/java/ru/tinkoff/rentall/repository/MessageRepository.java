package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.rentall.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
