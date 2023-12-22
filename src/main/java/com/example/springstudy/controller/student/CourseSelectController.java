package com.example.springstudy.controller.student;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.dto.SelectCourseDto;
import com.example.springstudy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseSelectController {
    private StudentService studentService;

    @Autowired
    public CourseSelectController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/course/select")
    public ResponseResult CourseSelect(@RequestBody SelectCourseDto selectCourseDto){
        return studentService.SelectCourse(selectCourseDto);
    }
}
