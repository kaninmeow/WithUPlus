package com.withu.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 聊天室实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 聊天室名称
     */
    private String roomName;

    /**
     * 志愿者用户ID
     */
    private Long volunteerUserId;

    /**
     * 消费者用户ID
     */
    private Long consumerUserId;

    /**
     * 聊天室状态：1-正常，0-关闭
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
