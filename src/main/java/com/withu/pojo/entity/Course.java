package com.withu.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 课程表实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程简介
     */
    private String courseIntro;

    /**
     * 章封面
     */
    private String chapterCover;

    /**
     * 课程类型（如：就医陪诊、居家照护、出行协助等）
     */
    private String courseType;

    /**
     * 课程难度系数(1-5)
     */
    private Integer courseLevel;

    /**
     * 课程积分值
     */
    private Integer courseScore;

    /**
     * 课程封面
     */
    private String courseCover;

    /**
     * 管理员id
     */
    private Long adminId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
