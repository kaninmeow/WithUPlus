package com.withu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.withu.mapper.ChatMessageMapper;
import com.withu.pojo.entity.ChatMessage;
import com.withu.pojo.vo.ChatMessageVO;
import com.withu.service.IChatMessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements IChatMessageService {

    @Override
    public ChatMessageVO sendMessage(Long roomId, Long senderId, Integer senderType, Integer messageType, String content) {
        ChatMessage message = ChatMessage.builder()
                .roomId(roomId)
                .senderId(senderId)
                .senderType(senderType)
                .messageType(messageType)
                .content(content)
                .isRead(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        
        this.save(message);
        
        return convertToVO(message);
    }

    @Override
    public List<ChatMessageVO> getUserChatMessages(Long userId) {
        QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(ChatMessage::getSenderId, userId)
                .orderByAsc(ChatMessage::getCreateTime);
        
        List<ChatMessage> messages = this.list(queryWrapper);
        return messages.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<ChatMessageVO> getRoomChatMessages(Long roomId) {
        QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(ChatMessage::getRoomId, roomId)
                .orderByAsc(ChatMessage::getCreateTime);
        
        List<ChatMessage> messages = this.list(queryWrapper);
        return messages.stream().map(this::convertToVO).collect(Collectors.toList());
    }
    
    private ChatMessageVO convertToVO(ChatMessage message) {
        ChatMessageVO vo = new ChatMessageVO();
        BeanUtils.copyProperties(message, vo);
        return vo;
    }
}
