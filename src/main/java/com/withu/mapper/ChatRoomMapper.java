package com.withu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.withu.pojo.entity.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

/**
 * 聊天室 Mapper 接口
 */
@Mapper
public interface ChatRoomMapper extends BaseMapper<ChatRoom> {
}
