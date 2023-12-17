package com.example.springstudy.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springstudy.entity.Course;
import org.apache.ibatis.annotations.Mapper;

@TableName("course")
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
