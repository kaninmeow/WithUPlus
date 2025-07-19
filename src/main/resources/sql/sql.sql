-- 志愿者用户表
CREATE TABLE volunteer_user (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
                                name VARCHAR(64) NOT NULL COMMENT '姓名',
                                username VARCHAR(64) NOT NULL COMMENT '用户名',
                                phone VARCHAR(20) NOT NULL COMMENT '电话',
                                password VARCHAR(128) NOT NULL COMMENT '密码（加密存储）',
                                sex TINYINT NOT NULL COMMENT '性别：0-未知 1-男 2-女',
                                id_number VARCHAR(18) COMMENT '身份证号',
                                avatar VARCHAR(255) COMMENT '头像URL',
                                create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
                                UNIQUE KEY uk_username (username),
                                UNIQUE KEY uk_phone (phone)
) COMMENT '志愿者用户表';

-- 普通消费者用户表
CREATE TABLE consumer_user (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
                               name VARCHAR(64) NOT NULL COMMENT '姓名',
                               username VARCHAR(64) NOT NULL COMMENT '用户名',
                               phone VARCHAR(20) NOT NULL COMMENT '电话',
                               password VARCHAR(128) NOT NULL COMMENT '密码（加密存储）',
                               sex TINYINT NOT NULL COMMENT '性别：0-未知 1-男 2-女',
                               id_number VARCHAR(18) COMMENT '身份证号',
                               avatar VARCHAR(255) COMMENT '头像URL',
                               create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
                               UNIQUE KEY uk_username (username),
                               UNIQUE KEY uk_phone (phone)
) COMMENT '普通消费者用户表';

-- 企业用户表
CREATE TABLE enterprise_user (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
                                 enterprise_name VARCHAR(128) NOT NULL COMMENT '企业名称',
                                 phone VARCHAR(20) NOT NULL COMMENT '电话',
                                 password VARCHAR(128) NOT NULL COMMENT '密码（加密存储）',
                                 enterprise_code VARCHAR(32) COMMENT '统一社会信用代码',
                                 avatar VARCHAR(255) COMMENT '头像URL',
                                 business_license VARCHAR(255) COMMENT '营业执照URL',
                                 create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
                                 UNIQUE KEY uk_phone (phone),
                                 UNIQUE KEY uk_enterprise_code (enterprise_code)
) COMMENT '企业用户表';

-- 老人信息表
CREATE TABLE elder (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
                       consumer_id BIGINT NOT NULL COMMENT '关联consumer_user.id',
                       name VARCHAR(64) NOT NULL COMMENT '姓名',
                       relation VARCHAR(32) NOT NULL COMMENT '与用户关系',
                       age TINYINT NOT NULL COMMENT '年龄',
                       phone VARCHAR(20) NOT NULL COMMENT '电话',
                       address VARCHAR(255) NOT NULL COMMENT '详细地址',
                       blood_type VARCHAR(10) COMMENT '血型：A/B/AB/O',
                       allergy_history TEXT COMMENT '过敏史',
                       FOREIGN KEY (consumer_id) REFERENCES consumer_user(id),
                       INDEX idx_consumer_id (consumer_id)
) COMMENT '老人信息表';

-- 常用药品表
CREATE TABLE commonly_used_drug (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
                                    elder_id BIGINT NOT NULL COMMENT '关联elder.id',
                                    drug_name VARCHAR(128) NOT NULL COMMENT '药品名称',
                                    drug_identification_code VARCHAR(64) COMMENT '药品识别码',
                                    FOREIGN KEY (elder_id) REFERENCES elder(id),
                                    INDEX idx_elder_id (elder_id)
) COMMENT '老人常用药品表';