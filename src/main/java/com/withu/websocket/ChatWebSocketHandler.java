package com.withu.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.withu.pojo.dto.ChatMessageDTO;
import com.withu.pojo.vo.ChatMessageVO;
import com.withu.service.IChatMessageService;
import com.withu.service.IChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket处理器
 */
@Slf4j
@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    @Autowired
    private IChatMessageService chatMessageService;
    
    @Autowired
    private IChatRoomService chatRoomService;

    // 房间 -> (用户 -> 会话)
    private static final ConcurrentHashMap<Long, ConcurrentHashMap<Long, WebSocketSession>> roomSessions = new ConcurrentHashMap<>();
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = (Long) session.getAttributes().get("userId");
        Long roomId = (Long) session.getAttributes().get("roomId");
        log.info("WebSocket连接建立: {}, userId={}, roomId={}", session.getId(), userId, roomId);
        if (userId != null && roomId != null) {
            roomSessions.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(userId, session);
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            String payload = ((TextMessage) message).getPayload();
            log.info("收到消息: {}", payload);
            
            try {
                ChatMessageDTO messageDTO = objectMapper.readValue(payload, ChatMessageDTO.class);
                handleChatMessage(session, messageDTO);
            } catch (Exception e) {
                log.error("处理消息失败: {}", e.getMessage());
                sendErrorMessage(session, "消息格式错误");
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket传输错误: {}", exception.getMessage());
        removeUserSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("WebSocket连接关闭: {}, 状态: {}", session.getId(), closeStatus);
        removeUserSession(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 处理聊天消息
     */
    private void handleChatMessage(WebSocketSession session, ChatMessageDTO messageDTO) {
        try {
            // 保存消息到数据库
            ChatMessageVO savedMessage = chatMessageService.sendMessage(
                    messageDTO.getRoomId(),
                    messageDTO.getSenderId(),
                    messageDTO.getSenderType(),
                    messageDTO.getMessageType(),
                    messageDTO.getContent()
            );
            
            // 确保当前会话已登记到房间
            Long roomId = messageDTO.getRoomId();
            Long senderId = messageDTO.getSenderId();
            if (roomId != null && senderId != null) {
                roomSessions.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(senderId, session);
            }

            // 广播消息给同房间其他用户
            broadcastMessage(roomId, savedMessage, senderId);
            
        } catch (Exception e) {
            log.error("处理聊天消息失败: {}", e.getMessage());
            sendErrorMessage(session, "发送消息失败");
        }
    }

    /**
     * 广播消息
     */
    private void broadcastMessage(Long roomId, ChatMessageVO message, Long senderId) {
        ConcurrentHashMap<Long, WebSocketSession> sessions = roomSessions.get(roomId);
        if (sessions == null) {
            return;
        }
        for (Map.Entry<Long, WebSocketSession> entry : sessions.entrySet()) {
            Long userId = entry.getKey();
            WebSocketSession s = entry.getValue();
            if (s != null && s.isOpen() && !userId.equals(senderId)) {
                try {
                    String json = objectMapper.writeValueAsString(message);
                    s.sendMessage(new TextMessage(json));
                } catch (IOException e) {
                    log.error("发送消息失败: {}", e.getMessage());
                }
            }
        }
    }

    /**
     * 发送错误消息
     */
    private void sendErrorMessage(WebSocketSession session, String errorMessage) {
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage("{\"error\":\"" + errorMessage + "\"}"));
            }
        } catch (IOException e) {
            log.error("发送错误消息失败: {}", e.getMessage());
        }
    }

    /**
     * 移除用户会话
     */
    private void removeUserSession(WebSocketSession session) {
        Long roomId = (Long) session.getAttributes().get("roomId");
        Long userId = (Long) session.getAttributes().get("userId");
        if (roomId != null && userId != null) {
            ConcurrentHashMap<Long, WebSocketSession> sessions = roomSessions.get(roomId);
            if (sessions != null) {
                sessions.remove(userId);
                if (sessions.isEmpty()) {
                    roomSessions.remove(roomId);
                }
            }
        }
    }
}