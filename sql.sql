create table address
(
    id               bigint auto_increment comment '自增主键'
        primary key,
    elder_id         bigint      null comment '老人id',
    type             varchar(20) null comment '地址类型',
    name             varchar(64) null comment '姓名',
    phone            varchar(20) null comment '电话',
    province         varchar(32) null,
    city             varchar(32) null,
    district         varchar(32) null,
    detailed_address varchar(32) null,
    consumer_id      bigint      null
)
    comment '地址表';

create table commonly_used_drugs
(
    id                       bigint auto_increment comment '自增主键'
        primary key,
    elder_id                 bigint       not null comment '老人id',
    drug_name                varchar(128) not null comment '药物名称',
    drug_identification_code varchar(64)  null comment '药物识别码'
)
    comment '老人常用药品表';

create table consumer_user
(
    id          bigint auto_increment comment '自增主键'
        primary key,
    name        varchar(64)  not null comment '姓名',
    username    varchar(64)  not null comment '用户名',
    phone       varchar(20)  not null comment '电话',
    password    varchar(128) not null comment '密码',
    sex         tinyint      not null comment '性别',
    id_number   varchar(18)  null comment '身份账号',
    avatar      varchar(255) null comment '头像URL',
    create_time datetime     null comment '注册时间'
)
    comment '普通消费者用户表';

create table elder
(
    id              bigint auto_increment comment '自增主键'
        primary key,
    consumer_id     bigint       not null comment '消费者id',
    name            varchar(64)  not null comment '姓名',
    relation        varchar(32)  not null comment '关系',
    age             tinyint      not null comment '年龄',
    phone           varchar(20)  not null comment '电话',
    address         varchar(255) not null comment '地址',
    blood_type      varchar(10)  null comment '血型',
    allergy_history text         null comment '过敏史',
    avatar          varchar(128) null
)
    comment '老人信息表';

create table enterprise_users
(
    id               bigint auto_increment comment '自增主键'
        primary key,
    username         varchar(64)  not null comment '企业账号',
    phone            varchar(20)  null comment '电话',
    password         varchar(255) not null comment '密码（加密存储）',
    name             varchar(100) null comment '企业名称',
    id_number        varchar(18)  null comment '统一社会信用代码',
    avatar           varchar(255) null comment '头像URL',
    business_license varchar(255) null comment '营业执照URL',
    create_time      datetime     null comment '注册时间',
    constraint phone
        unique (phone),
    constraint username
        unique (username)
)
    comment '企业用户表';

create table `order`
(
    id                       bigint auto_increment comment '自增主键'
        primary key,
    elder_id                 bigint            not null comment '老人id',
    service_type             varchar(32)       not null comment '服务类型',
    service_description      text              null comment '服务详细说明',
    start_time               datetime          not null comment '开始时间',
    end_time                 datetime          null comment '结束时间',
    is_volunteer_payment     tinyint default 0 null comment '是否需要志愿者代付',
    volunteer_payment_amount decimal(10, 2)    null comment '代付金额',
    volunteer_payment_status tinyint           null comment '代付状态',
    volunteer_payment_time   datetime          null comment '支付时间',
    payment_proof            varchar(255)      null comment '支付凭证URL',
    service_fee              decimal(10, 2)    null comment '订单服务费',
    advanced_payment_amount  decimal(10, 2)    null comment '代付预收款',
    payment_type             tinyint           null comment '支付类型 1：微信 2：支付宝',
    status                   tinyint default 0 null comment '状态 0：接单 1：进行中 2：待评价 3：完成 4：审核中 -1：取消订单',
    create_time              datetime          null comment '创建时间',
    consumer_id              bigint            not null,
    volunteer_user_id        bigint            null comment '志愿者id'
)
    comment '订单表';

create table service_address
(
    id       bigint auto_increment comment '自增主键'
        primary key,
    order_id bigint      null comment '订单id',
    type     varchar(20) null comment '地址类型',
    name     varchar(64) null comment '姓名',
    phone    varchar(20) null comment '电话'
)
    comment '服务地址表';

create table volunteer_users
(
    id          bigint auto_increment comment '自增主键'
        primary key,
    name        varchar(64)  not null comment '姓名',
    username    varchar(64)  not null comment '用户名',
    phone       varchar(20)  not null comment '电话',
    password    varchar(128) not null comment '密码',
    sex         tinyint      not null comment '性别',
    id_number   varchar(18)  null comment '身份账号',
    avatar      varchar(255) null comment '头像URL',
    create_time datetime     null comment '注册时间'
)
    comment '志愿者用户表';

-- 课程表
create table course (
                        id              bigint auto_increment comment '主键id' primary key,
                        course_name     varchar(128) not null comment '课程名称',
                        course_intro    text comment '课程简介',
                        chapter_cover   varchar(255) comment '章封面',
                        course_type     varchar(64) comment '课程类型',
                        course_level    tinyint not null default 1 comment '课程难度系数(1-5)',
                        course_score    int not null default 0 comment '课程积分值',
                        course_cover    varchar(255) comment '课程封面',
                        admin_id        bigint not null comment '管理员id',
                        create_time     datetime not null default current_timestamp comment '创建时间',
                        update_time     datetime not null default current_timestamp on update current_timestamp comment '修改时间'
) comment = '课程表';

-- 课程内容 - 章
create table course_chapter (
                                id              bigint auto_increment comment '主键id' primary key,
                                chapter_order   int not null comment '章顺序(第几章)',
                                chapter_name    varchar(128) not null comment '章名称',
                                course_id       bigint not null comment '课程id',
                                create_time     datetime not null default current_timestamp comment '创建时间',
                                update_time     datetime not null default current_timestamp on update current_timestamp comment '修改时间',
                                constraint fk_chapter_course foreign key (course_id) references course (id)
) comment = '课程内容_章表';

-- 课程内容 - 节
create table course_section (
                                id              bigint auto_increment comment '主键id' primary key,
                                section_order   int not null comment '节顺序(第几节)',
                                section_name    varchar(128) not null comment '节名称',
                                video_url       varchar(512) comment '视频链接',
                                chapter_id      bigint not null comment '章id',
                                section_cover   varchar(255) comment '节封面',
                                create_time     datetime not null default current_timestamp comment '创建时间',
                                update_time     datetime not null default current_timestamp on update current_timestamp comment '修改时间',
                                constraint fk_section_chapter foreign key (chapter_id) references course_chapter (id)
) comment = '课程内容_节表';
