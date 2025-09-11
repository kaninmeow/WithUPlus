package com.withu.controller;

import com.withu.pojo.entity.Course;
import com.withu.pojo.vo.CourseDetailVO;
import com.withu.pojo.dto.CourseCreateDTO;
import com.withu.result.Result;
import com.withu.service.ICourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  课程
 */
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    ICourseService courseService;

    /**
     * 创建课程
     * @param courseCreateDTO
     * @return
     */
    @PostMapping("/create")
    public Result createCourse(CourseCreateDTO courseCreateDTO) {
        courseService.createCourse(courseCreateDTO);
        return Result.success("创建成功");
    }

    /**
     * 获取所有课程（不包含章节）
     * @return Result<List<Course>>
     */
    @GetMapping("/list")
    public Result<java.util.List<Course>> listAllCourses() {
        return Result.success("查询成功", courseService.list());
    }

    /**
     * 根据课程ID获取课程详情
     */
    @GetMapping("/detail/{id}")
    public Result<CourseDetailVO> getCourseDetail(@org.springframework.web.bind.annotation.PathVariable("id") Long id) {
        return Result.success("查询成功", courseService.getCourseDetail(id));
    }

    /**
     * 根据课程ID删除课程（级联删除章节与小节）
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteCourse(@org.springframework.web.bind.annotation.PathVariable("id") Long id) {
        boolean ok = courseService.deleteCourseCascade(id);
        return ok ? Result.success("删除成功") : Result.error("删除失败");
    }
}
