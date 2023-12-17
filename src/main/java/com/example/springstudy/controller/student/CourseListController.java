package com.example.springstudy.controller.student;

import com.example.springstudy.domain.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseListController {
    @GetMapping("/course/list")
    public ResponseResult courseShow(){
        return null;
    }
}
