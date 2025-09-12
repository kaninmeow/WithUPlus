package com.withu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.withu.mapper.CourseChapterMapper;
import com.withu.mapper.CourseMapper;
import com.withu.mapper.CourseSectionMapper;
import com.withu.pojo.dto.CourseCreateDTO;
import com.withu.pojo.dto.CourseUpdateDTO;
import com.withu.pojo.entity.Course;
import com.withu.pojo.entity.CourseChapter;
import com.withu.pojo.entity.CourseSection;
import com.withu.pojo.vo.CourseDetailVO;
import com.withu.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired
    CourseChapterMapper courseChapterMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseSectionMapper courseSectionMapper;
    @Override
    public void createCourse(CourseCreateDTO courseCreateDTO) {
        //插入课程
        Course course = Course.builder()
                .courseName(courseCreateDTO.getCourseName())
                .courseIntro(courseCreateDTO.getCourseIntro())
                .courseCover(courseCreateDTO.getCourseCover())
                .courseType(courseCreateDTO.getCourseType())
                .courseLevel(Integer.parseInt(courseCreateDTO.getCourseLevel()))
                .courseScore(Integer.parseInt(courseCreateDTO.getCourseScore()))
                .courseCover(courseCreateDTO.getCourseCover())
                .adminId(Long.parseLong(courseCreateDTO.getAdminId()))
                .createTime(courseCreateDTO.getCreateTime())
                .updateTime(courseCreateDTO.getUpdateTime())
                .build();
        baseMapper.insert(course);
        // 插入课程章节
        List<CourseChapter> courseChapters = courseCreateDTO.getCourseChapters();
        for (CourseChapter courseChapter : courseChapters) {
            courseChapter.setCourseId(course.getId());
        }
        courseChapterMapper.insertBatch(courseChapters);
    }

    @Override
    public CourseDetailVO getCourseDetail(Long courseId) {
        Course course = baseMapper.selectById(courseId);
        if (course == null) {
            return null;
        }
        QueryWrapper<CourseChapter> cw = new QueryWrapper<>();
        cw.lambda().eq(CourseChapter::getCourseId, courseId).orderByAsc(CourseChapter::getChapterOrder);
        List<CourseChapter> courseChapters = courseChapterMapper.selectList(cw);

        QueryWrapper<CourseSection> sw = new QueryWrapper<>();
        sw.lambda().inSql(CourseSection::getChapterId, "select id from course_chapter where course_id = " + courseId)
                .orderByAsc(CourseSection::getSectionOrder);
        List<CourseSection> courseSections = courseSectionMapper.selectList(sw);

        return CourseDetailVO.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .courseIntro(course.getCourseIntro())
                .chapterCover(course.getChapterCover())
                .courseType(course.getCourseType())
                .courseLevel(course.getCourseLevel())
                .courseScore(course.getCourseScore())
                .courseCover(course.getCourseCover())
                .adminId(course.getAdminId())
                .createTime(course.getCreateTime())
                .updateTime(course.getUpdateTime())
                .courseChapters(courseChapters)
                .courseSections(courseSections)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCourseCascade(Long courseId) {
        // 先删小节：根据课程ID定位到所有章节ID，再删小节
        QueryWrapper<CourseSection> sw = new QueryWrapper<>();
        sw.lambda().inSql(CourseSection::getChapterId, "select id from course_chapter where course_id = " + courseId);
        courseSectionMapper.delete(sw);

        // 再删章节
        QueryWrapper<CourseChapter> cw = new QueryWrapper<>();
        cw.lambda().eq(CourseChapter::getCourseId, courseId);
        courseChapterMapper.delete(cw);

        // 最后删课程
        return this.removeById(courseId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCourse(CourseUpdateDTO courseUpdateDTO) {
        // 更新课程基本信息
        Course course = Course.builder()
                .id(courseUpdateDTO.getId())
                .courseName(courseUpdateDTO.getCourseName())
                .courseIntro(courseUpdateDTO.getCourseIntro())
                .courseCover(courseUpdateDTO.getCourseCover())
                .courseType(courseUpdateDTO.getCourseType())
                .courseLevel(Integer.parseInt(courseUpdateDTO.getCourseLevel()))
                .courseScore(Integer.parseInt(courseUpdateDTO.getCourseScore()))
                .adminId(Long.parseLong(courseUpdateDTO.getAdminId()))
                .updateTime(courseUpdateDTO.getUpdateTime())
                .build();
        
        boolean courseUpdated = this.updateById(course);
        if (!courseUpdated) {
            return false;
        }

        // 删除原有章节和小节
        QueryWrapper<CourseSection> sw = new QueryWrapper<>();
        sw.lambda().inSql(CourseSection::getChapterId, "select id from course_chapter where course_id = " + courseUpdateDTO.getId());
        courseSectionMapper.delete(sw);

        QueryWrapper<CourseChapter> cw = new QueryWrapper<>();
        cw.lambda().eq(CourseChapter::getCourseId, courseUpdateDTO.getId());
        courseChapterMapper.delete(cw);

        // 插入新的章节
        List<CourseChapter> courseChapters = courseUpdateDTO.getCourseChapters();
        if (courseChapters != null && !courseChapters.isEmpty()) {
            for (CourseChapter courseChapter : courseChapters) {
                courseChapter.setCourseId(courseUpdateDTO.getId());
            }
            courseChapterMapper.insertBatch(courseChapters);
        }

        return true;
    }
}