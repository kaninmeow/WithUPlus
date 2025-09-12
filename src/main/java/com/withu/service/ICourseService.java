package com.withu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.withu.pojo.dto.CourseCreateDTO;
import com.withu.pojo.dto.CourseUpdateDTO;
import com.withu.pojo.entity.Course;
import com.withu.pojo.vo.CourseDetailVO;

/**
 * 课程表 服务接口
 */
public interface ICourseService extends IService<Course> {
    void createCourse(CourseCreateDTO courseCreateDTO);

    /**
     * 根据课程ID获取课程详情（包含章节与小节）
     */
    CourseDetailVO getCourseDetail(Long courseId);

    /**
     * 根据课程ID删除课程并级联删除章节与小节
     */
    boolean deleteCourseCascade(Long courseId);

    /**
     * 更新课程信息并更新章节
     */
    boolean updateCourse(CourseUpdateDTO courseUpdateDTO);
}


