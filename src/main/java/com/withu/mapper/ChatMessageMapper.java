package com.withu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.withu.pojo.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 聊天消息 Mapper 接口
 */
@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {
}
