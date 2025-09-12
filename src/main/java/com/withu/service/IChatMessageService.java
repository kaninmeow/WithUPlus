package com.withu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.withu.pojo.entity.ChatMessage;
import com.withu.pojo.vo.ChatMessageVO;

import java.util.List;

/**
 * 聊天消息 服务接口
 */
public interface IChatMessageService extends IService<ChatMessage> {
    
    /**
     * 发送消息
     */
    ChatMessageVO sendMessage(Long volunteerUserId, Long consumerUserId, Long senderId, Integer senderType, Integer messageType, String content);
    
    /**
     * 获取用户的所有聊天记录
     */
    List<ChatMessageVO> getUserChatMessages(Long userId);
    
    /**
     * 获取聊天室的消息记录
     */
    List<ChatMessageVO> getDialogMessages(Long volunteerUserId, Long consumerUserId);
}
