//package com.example.springstudy.controller.student;
//
//import com.example.springstudy.domain.ResponseResult;
//import com.example.springstudy.entity.CourseView;
//import com.example.springstudy.service.StudentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class CourseListController {
//    private StudentService studentService;
//
//    @Autowired
//    public CourseListController(StudentService studentService) {
//        this.studentService = studentService;
//    }
//
//    @GetMapping("/course/list")
//    public List<CourseView> courseShow(){
//
//        return studentService.ShowCourses();
//    }
//}
