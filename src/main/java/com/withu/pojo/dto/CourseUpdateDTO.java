package com.withu.pojo.dto;

import com.withu.pojo.entity.CourseChapter;
import com.withu.pojo.entity.CourseSection;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "更新课程时传递的数据模型")
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateDTO implements Serializable {
    private Long id;
    private String courseName;
    private String courseIntro;
    private String courseCover;
    private String courseType;
    private String courseLevel;
    private String courseScore;
    private String adminId;
    private LocalDateTime updateTime;
    List<CourseChapter> courseChapters;
    List<CourseSection> courseSections;
}
