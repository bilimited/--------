package com.example.springstudy.controller.teacher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.Course;
import com.example.springstudy.entity.CourseView;
import com.example.springstudy.entity.dto.TempCnoDto;
import com.example.springstudy.mapper.CourseMapper;
import com.example.springstudy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateCourseController {

    private TeacherService teacherService;
    private CourseMapper courseMapper;
    @Autowired
    public UpdateCourseController(TeacherService teacherService,CourseMapper courseMapper){
        this.teacherService = teacherService;
        this.courseMapper = courseMapper;
    }

    @PostMapping("teacher/updateCourse")
    public ResponseResult UpdateCourse(@RequestBody Course course){
        return teacherService.UpdateCourse(course);
    }

    @PostMapping("teacher/getCourseInfo")
    public ResponseResult GetCourseInfo(@RequestBody TempCnoDto cnoDto){
        Course course = courseMapper.selectOne(new QueryWrapper<Course>().eq("cno",cnoDto.getCno()));
        System.out.println("$$$$$$$$$$OK");
        return ResponseResult.okResult(course);
    }
}
