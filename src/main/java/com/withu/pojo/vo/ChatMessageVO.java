package com.withu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 聊天消息VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageVO {
    
    /**
     * 消息ID
     */
    private Long id;
    
    /**
     * 志愿者用户ID
     */
    private Long volunteerUserId;

    /**
     * 消费者用户ID
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
    
    /**
     * 是否已读：1-已读，0-未读
     */
    private Integer isRead;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
