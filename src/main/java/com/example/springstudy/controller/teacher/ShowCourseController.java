package com.example.springstudy.controller.teacher;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.Course;
import com.example.springstudy.entity.dto.TempTnoDto;
import com.example.springstudy.service.TeacherService;
import com.example.springstudy.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShowCourseController {
    private TeacherService teacherService;
    @Autowired
    public ShowCourseController(TeacherService teacherService ){
        this.teacherService = teacherService;
    }

    @GetMapping("/teacher/showcourse")
    public ResponseResult ShowCourse(){
        return teacherService.GetTeachingCourses(teacherService.GetTeacher().getTno());
    }

}
