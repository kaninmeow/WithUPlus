package com.withu.controller;

import com.withu.annotation.IgnoreAuth;
import com.withu.pojo.vo.ChatMessageVO;
import com.withu.result.Result;
import com.withu.service.IChatMessageService;
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
     * 获取志愿者与消费者之间的消息记录
     */
    @GetMapping("/dialog/messages")
    @IgnoreAuth
    public Result<List<ChatMessageVO>> getDialogMessages(@RequestParam Long volunteerUserId,
                                                         @RequestParam Long consumerUserId) {
        log.info("获取对话消息记录: volunteer={}, consumer={}", volunteerUserId, consumerUserId);
        List<ChatMessageVO> messages = chatMessageService.getDialogMessages(volunteerUserId, consumerUserId);
        return Result.success("查询成功", messages);
    }

}
