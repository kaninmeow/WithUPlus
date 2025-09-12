package com.withu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.withu.pojo.entity.CourseChapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 课程-章 Mapper 接口
 */
@Mapper
public interface CourseChapterMapper extends BaseMapper<CourseChapter> {
    void insertBatch(List<CourseChapter> courseChapters);
}


