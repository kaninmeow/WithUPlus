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

    // 存储用户ID和WebSocketSession的映射
    private static final ConcurrentHashMap<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("WebSocket连接建立: {}", session.getId());
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
            
            // 存储用户会话
            userSessions.put(messageDTO.getSenderId(), session);
            
            // 广播消息给聊天室的其他用户
            broadcastMessage(messageDTO.getRoomId(), savedMessage, messageDTO.getSenderId());
            
        } catch (Exception e) {
            log.error("处理聊天消息失败: {}", e.getMessage());
            sendErrorMessage(session, "发送消息失败");
        }
    }

    /**
     * 广播消息
     */
    private void broadcastMessage(Long roomId, ChatMessageVO message, Long senderId) {
        // 这里简化处理，实际应该根据roomId找到聊天室的所有用户
        // 然后发送给除了发送者之外的其他用户
        userSessions.forEach((userId, session) -> {
            if (!userId.equals(senderId) && session.isOpen()) {
                try {
                    String messageJson = objectMapper.writeValueAsString(message);
                    session.sendMessage(new TextMessage(messageJson));
                } catch (IOException e) {
                    log.error("发送消息失败: {}", e.getMessage());
                }
            }
        });
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
        userSessions.entrySet().removeIf(entry -> entry.getValue().equals(session));
    }
}