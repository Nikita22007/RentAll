package ru.rsreu.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.rentall.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
