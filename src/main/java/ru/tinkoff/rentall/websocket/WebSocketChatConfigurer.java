package ru.tinkoff.rentall.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import ru.tinkoff.rentall.service.MessageService;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketChatConfigurer implements WebSocketConfigurer {
    private final MessageService messageService;

    public WebSocketChatConfigurer(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(webSocketChatHandler(this.messageService), "/chat")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                                WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        Map<String, String> queryParams = getQueryParams(request.getURI());
                        attributes.put("receiver", queryParams.get("receiver"));
                        attributes.put("sender", queryParams.get("sender"));
                        attributes.put("chatId", queryParams.get("chatId"));
                        super.beforeHandshake(request, response, wsHandler, attributes);
                        return true;
                    }

                    private Map<String, String> getQueryParams(URI uri) {
                        Map<String, String> queryParams = new HashMap<>();
                        String[] pairs = uri.getQuery().split("&");
                        for (String pair : pairs) {
                            String[] keyValue = pair.split("=");
                            queryParams.put(keyValue[0], keyValue[1]);
                        }
                        return queryParams;
                    }
                });
    }

    @Bean
    public WebSocketHandler webSocketChatHandler(MessageService messageService) {
        return new WebSocketChatHandler(messageService);
    }
}

