-- 聊天功能相关表SQL

-- 1. 聊天室表
CREATE TABLE `chat_room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `room_name` varchar(100) DEFAULT NULL COMMENT '聊天室名称',
  `volunteer_user_id` bigint(20) NOT NULL COMMENT '志愿者用户ID',
  `consumer_user_id` bigint(20) NOT NULL COMMENT '消费者用户ID',
  `status` tinyint(1) DEFAULT 1 COMMENT '聊天室状态：1-正常，0-关闭',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_volunteer_consumer` (`volunteer_user_id`, `consumer_user_id`),
  KEY `idx_volunteer_user_id` (`volunteer_user_id`),
  KEY `idx_consumer_user_id` (`consumer_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天室表';

-- 2. 聊天消息表
CREATE TABLE `chat_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `room_id` bigint(20) NOT NULL COMMENT '聊天室ID',
  `sender_id` bigint(20) NOT NULL COMMENT '发送者ID',
  `sender_type` tinyint(1) NOT NULL COMMENT '发送者类型：1-志愿者，2-消费者',
  `message_type` tinyint(1) DEFAULT 1 COMMENT '消息类型：1-文本，2-图片，3-文件',
  `content` text NOT NULL COMMENT '消息内容',
  `is_read` tinyint(1) DEFAULT 0 COMMENT '是否已读：1-已读，0-未读',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_room_id` (`room_id`),
  KEY `idx_sender_id` (`sender_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天消息表';
