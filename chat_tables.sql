-- 聊天功能相关表SQL（无聊天室模型）

-- 1. 聊天消息表（按志愿者ID + 消费者ID 维度存储）
CREATE TABLE IF NOT EXISTS `chat_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `volunteer_user_id` bigint(20) NOT NULL COMMENT '志愿者用户ID',
  `consumer_user_id` bigint(20) NOT NULL COMMENT '消费者用户ID',
  `sender_id` bigint(20) NOT NULL COMMENT '发送者ID',
  `sender_type` tinyint(1) NOT NULL COMMENT '发送者类型：1-志愿者，2-消费者',
  `message_type` tinyint(1) DEFAULT 1 COMMENT '消息类型：1-文本，2-图片，3-文件',
  `content` text NOT NULL COMMENT '消息内容',
  `is_read` tinyint(1) DEFAULT 0 COMMENT '是否已读：1-已读，0-未读',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_volunteer_consumer` (`volunteer_user_id`, `consumer_user_id`),
  KEY `idx_sender_id` (`sender_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天消息表（无聊天室）';

-- 若历史上已存在 chat_room / chat_message(room_id) 请执行迁移：
-- ALTER TABLE chat_message ADD COLUMN volunteer_user_id bigint(20) NOT NULL AFTER id;
-- ALTER TABLE chat_message ADD COLUMN consumer_user_id bigint(20) NOT NULL AFTER volunteer_user_id;
-- -- 根据业务逻辑把旧数据的room_id映射回 volunteer_user_id/consumer_user_id（若能推导）
-- ALTER TABLE chat_message DROP COLUMN room_id;
-- DROP TABLE IF EXISTS chat_room;
