package com.example.springstudy.controller.teacher;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.Teacher;
import com.example.springstudy.entity.dto.SetScoreDto;
import com.example.springstudy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SetScoreController {
    private TeacherService teacherService;

    @Autowired
    public SetScoreController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/teacher/wgrade")
    public ResponseResult SetScore(@RequestBody List<SetScoreDto> setScoreDto){
        return teacherService.SerStudentScore(setScoreDto);
    }
}
