package com.example.springstudy.controller.teacher;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.Course;
import com.example.springstudy.entity.dto.SetCourseProgressDto;
import com.example.springstudy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 改变课程进度的接口
@RestController
public class ChangeProgressController {
    private TeacherService teacherService;

    @Autowired
    public ChangeProgressController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/teacher/teaching")
    public ResponseResult ChangeProgress(@RequestBody SetCourseProgressDto setCourseProgressDto){
        return teacherService.SetCourseProgress(setCourseProgressDto);
    }

}
