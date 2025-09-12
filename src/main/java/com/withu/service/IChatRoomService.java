package com.withu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.withu.pojo.entity.ChatRoom;

/**
 * 聊天室 服务接口
 */
public interface IChatRoomService extends IService<ChatRoom> {
    
    /**
     * 根据志愿者和消费者ID获取或创建聊天室
     */
    ChatRoom getOrCreateChatRoom(Long volunteerUserId, Long consumerUserId);
}
