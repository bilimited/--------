package com.example.springstudy.controller.teacher;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.dto.TempCnoDto;
import com.example.springstudy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 展示教师对应课程所教学的学生
@RestController
public class GetLearnStuController {
    private TeacherService teacherService;

    @Autowired
    public GetLearnStuController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/teacher/showstu")
    public ResponseResult GetLearnStu(@RequestBody TempCnoDto t){
        System.out.println("controller cno = " + t.getCno());
        return teacherService.GetLearnStudents(t.getCno());
    }

}
