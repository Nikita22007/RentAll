package ru.tinkoff.rentall.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ChatHandler extends TextWebSocketHandler {
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection established on session: {}", session.getId());
        sessions.put(session.getId(), session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String messagePayload = message.getPayload();
        log.info("Message: {}", messagePayload);
        sendMessageToAllSessions(session, messagePayload);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Exception occurred: {} on session: {}", exception.getMessage(), session.getId());
        sessions.remove(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Connection closed on session: {} with status: {}", session.getId(), closeStatus.getCode());
        sessions.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void sendMessageToAllSessions(WebSocketSession session, String message) {
        TextMessage textMessage = new TextMessage(message);
        sessions.forEach((id, receiver) -> {
            try {
                if (!receiver.equals(session)) {
                    receiver.sendMessage(textMessage);
                }
            } catch (IllegalStateException | IOException e) {
                log.error("Error sending message to session: {}", id, e);
            }
        });
    }
}
