package com.example.springstudy.service;

import com.example.springstudy.entity.Course;
import com.example.springstudy.entity.CourseView;
import com.example.springstudy.entity.Student;
import com.example.springstudy.entity.User;
import com.example.springstudy.entity.dto.OpenCourseDto;
import com.example.springstudy.entity.dto.SelectCourseDto;

import java.util.List;

public interface StudentService {
    /**
     * 根据user对象获取对应的Student对象。
     * @param user
     * @return
     */
    public Student GetStudent(User user);
    public List<CourseView> GetLearningCourses(String sno);

    /**
     * 查询所有课程
     * @return
     */
    public List<CourseView> ShowCourses();
    public int SelectCourse(SelectCourseDto selectCourseDto);
}
