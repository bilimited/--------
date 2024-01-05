package com.example.springstudy.controller.student;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.dto.SetScoreDto;
import com.example.springstudy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


// 返回学生对应科目的成绩
@RestController
public class GetScoreController {
    private StudentService studentService;

    @Autowired
    public GetScoreController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 此处复用了教师设置成绩所输入的Dto
    @PostMapping("/student/getScore")
    public ResponseResult GetScore(@RequestBody SetScoreDto setScoreDto){
        System.out.println(setScoreDto);
        return studentService.GetScore(setScoreDto);
    }
}
