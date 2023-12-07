package com.example.springstudy.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springstudy.entity.CourseView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@TableName("course_view")
@Mapper
public interface CourseViewMapper extends BaseMapper<CourseView> {

    @Select("Select cv.* from course_view cv natural join student_course sc where sc.sno=#{sno}")
    public List<CourseView> GetCoursesBySno(String sno);


}
