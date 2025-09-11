package com.withu.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 课程内容 - 节 实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseSection {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 节顺序（第几节）
     */
    private Integer sectionOrder;

    /**
     * 节名称
     */
    private String sectionName;

    /**
     * 视频链接
     */
    private String videoUrl;

    /**
     * 章id（关联course_chapter表）
     */
    private Long chapterId;

    /**
     * 节封面
     */
    private String sectionCover;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
