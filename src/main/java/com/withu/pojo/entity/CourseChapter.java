package com.withu.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 课程内容 - 章 实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseChapter {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 章顺序（第几章）
     */
    private Integer chapterOrder;

    /**
     * 章名称
     */
    private String chapterName;

    /**
     * 课程id（关联course表）
     */
    private Long courseId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
