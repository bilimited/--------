package com.example.springstudy.controller.teacher;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.Course;
import com.example.springstudy.entity.Teacher;
import com.example.springstudy.entity.dto.OpenCouDto;
import com.example.springstudy.entity.dto.OpenCourseDto;
import com.example.springstudy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
// 这是教师选课的控制器
// 改变的数据库是course数据库，向course数据库中添加数据
// 在拦截器中已经对用户进行确认，因此这里只需要执行添加语句即可
@RestController
public class OpenCourseController {
    private TeacherService teacherService;

    @Autowired
    public OpenCourseController(TeacherService teacherService){
        this.teacherService = teacherService;
    }
    @PostMapping("/teacher/opencourse")
    public ResponseResult OpenCourse(@RequestBody Course openCouDto){
        // 调用teacher中的开课service方法
        openCouDto.setTno(teacherService.GetTeacher().getTno());
        System.out.println(openCouDto);
        return teacherService.OpenCourse(openCouDto);
    }
}
