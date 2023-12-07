package com.example.springstudy.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springstudy.entity.Student_course;
import org.apache.ibatis.annotations.Mapper;

@TableName("student_course")
@Mapper
public interface StudentCourseMapper extends BaseMapper<Student_course> {
}
