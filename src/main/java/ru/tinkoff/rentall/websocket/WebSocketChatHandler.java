package ru.tinkoff.rentall.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<TextMessage>> offlineMessages = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection established on session: {}", session.getId());
        String receiver = (String) session.getAttributes().get("receiver");
        String sender = (String) session.getAttributes().get("sender");
        log.info("Receiver ID: {}", receiver);
        log.info("Sender ID: {}", sender);
        this.sessions.put(sender, session);
        List<TextMessage> messages = this.offlineMessages.get(sender);
        if (messages != null) {
            for (TextMessage message : messages) {
                session.sendMessage(message);
            }
            this.offlineMessages.put(sender, new ArrayList<>());
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String messagePayload = message.getPayload();
        log.info("Message: {}", messagePayload);
        String receiver = (String) session.getAttributes().get("receiver");
        WebSocketSession receiverSession = this.sessions.get(receiver);
        if (receiverSession != null) {
            receiverSession.sendMessage(message);
            return;
        }
        this.offlineMessages.compute(receiver, (key, existingMessages) -> {
            if (existingMessages == null) {
                existingMessages = new ArrayList<>();
            }
            existingMessages.add(message);
            return existingMessages;
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Exception occurred: {} on session: {}", exception.getMessage(), session.getId());
        String sender = (String) session.getAttributes().get("sender");
        this.sessions.remove(sender);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Connection closed on session: {} with status: {}", session.getId(), closeStatus.getCode());
        String sender = (String) session.getAttributes().get("sender");
        this.sessions.remove(sender);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
