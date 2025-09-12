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
     * 发起方为志愿者时：志愿者ID；为消费者时也需要传本人的志愿者ID或消费者ID
     */
    private Long volunteerUserId;

    /**
     * 对端的消费者ID（或本人的消费者ID），用于确定会话
     */
    private Long consumerUserId;

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
