package com.withu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.withu.mapper.ChatRoomMapper;
import com.withu.pojo.entity.ChatRoom;
import com.withu.service.IChatRoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatRoomServiceImpl extends ServiceImpl<ChatRoomMapper, ChatRoom> implements IChatRoomService {

    @Override
    public ChatRoom getOrCreateChatRoom(Long volunteerUserId, Long consumerUserId) {
        // 先查找是否已存在聊天室
        QueryWrapper<ChatRoom> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(ChatRoom::getVolunteerUserId, volunteerUserId)
                .eq(ChatRoom::getConsumerUserId, consumerUserId)
                .eq(ChatRoom::getStatus, 1);
        
        ChatRoom existingRoom = this.getOne(queryWrapper);
        if (existingRoom != null) {
            return existingRoom;
        }
        
        // 创建新的聊天室
        ChatRoom newRoom = ChatRoom.builder()
                .roomName("聊天室_" + volunteerUserId + "_" + consumerUserId)
                .volunteerUserId(volunteerUserId)
                .consumerUserId(consumerUserId)
                .status(1)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        
        this.save(newRoom);
        return newRoom;
    }
}
