package com.withu.controller;

import com.withu.annotation.IgnoreAuth;
import com.withu.pojo.entity.ChatRoom;
import com.withu.pojo.vo.ChatMessageVO;
import com.withu.result.Result;
import com.withu.service.IChatMessageService;
import com.withu.service.IChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 聊天控制器
 */
@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private IChatMessageService chatMessageService;
    
    @Autowired
    private IChatRoomService chatRoomService;

    /**
     * 获取用户的所有聊天记录
     */
    @GetMapping("/messages/{userId}")
    @IgnoreAuth
    public Result<List<ChatMessageVO>> getUserChatMessages(@PathVariable Long userId) {
        log.info("获取用户聊天记录: {}", userId);
        List<ChatMessageVO> messages = chatMessageService.getUserChatMessages(userId);
        return Result.success("查询成功", messages);
    }

    /**
     * 获取聊天室的消息记录
     */
    @GetMapping("/room/{roomId}/messages")
    @IgnoreAuth
    public Result<List<ChatMessageVO>> getRoomChatMessages(@PathVariable Long roomId) {
        log.info("获取聊天室消息记录: {}", roomId);
        List<ChatMessageVO> messages = chatMessageService.getRoomChatMessages(roomId);
        return Result.success("查询成功", messages);
    }

    /**
     * 创建或获取聊天室
     */
    @PostMapping("/room")
    @IgnoreAuth
    public Result<ChatRoom> createOrGetChatRoom(@RequestParam Long volunteerUserId, 
                                               @RequestParam Long consumerUserId) {
        log.info("创建或获取聊天室: 志愿者={}, 消费者={}", volunteerUserId, consumerUserId);
        ChatRoom room = chatRoomService.getOrCreateChatRoom(volunteerUserId, consumerUserId);
        return Result.success("操作成功", room);
    }
}
