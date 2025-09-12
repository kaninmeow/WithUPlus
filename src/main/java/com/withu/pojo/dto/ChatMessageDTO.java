package com.withu.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天消息DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    
    /**
     * 聊天室ID
     */
    private Long roomId;
    
    /**
     * 发送者ID
     */
    private Long senderId;
    
    /**
     * 发送者类型：1-志愿者，2-消费者
     */
    private Integer senderType;
    
    /**
     * 消息类型：1-文本，2-图片，3-文件
     */
    private Integer messageType;
    
    /**
     * 消息内容
     */
    private String content;
}
