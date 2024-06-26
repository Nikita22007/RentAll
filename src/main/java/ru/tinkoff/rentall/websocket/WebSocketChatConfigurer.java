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

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketChatConfigurer implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(webSocketChatHandler(), "/chat")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                                WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        Map<String, String> queryParams = getQueryParams(request.getURI());
                        attributes.put("receiver", queryParams.get("receiver"));
                        attributes.put("sender", queryParams.get("sender"));
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
    public WebSocketHandler webSocketChatHandler() {
        return new WebSocketChatHandler();
    }
}

