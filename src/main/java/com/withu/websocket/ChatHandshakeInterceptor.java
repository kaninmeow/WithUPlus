package com.withu.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Map;

@Slf4j
@Component
public class ChatHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        try {
            URI uri = request.getURI();
            String query = uri.getQuery();
            if (query != null) {
                String[] params = query.split("&");
                for (String p : params) {
                    String[] kv = p.split("=", 2);
                    if (kv.length == 2) {
                        if ("userId".equals(kv[0])) {
                            attributes.put("userId", Long.parseLong(kv[1]));
                        } else if ("roomId".equals(kv[0])) {
                            attributes.put("roomId", Long.parseLong(kv[1]));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("握手参数解析失败: {}", e.getMessage());
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}


