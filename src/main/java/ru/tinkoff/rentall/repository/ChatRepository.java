package ru.tinkoff.rentall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.rentall.composite_id.ChatId;
import ru.tinkoff.rentall.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, ChatId> {

}
