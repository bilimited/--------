package com.example.springstudy.service;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.Course;
import com.example.springstudy.entity.CourseView;
import com.example.springstudy.entity.Student;
import com.example.springstudy.entity.User;
import com.example.springstudy.entity.dto.OpenCourseDto;
import com.example.springstudy.entity.dto.SelectCourseDto;
import com.example.springstudy.entity.dto.SetScoreDto;

import java.util.List;

public interface StudentService {
    /**
     * 根据user对象获取对应的Student对象。
     * @param user
     * @return
     */
    public Student GetStudent(User user);
    public ResponseResult GetLearningCourses(long sno);

    /**
     * 查询所有课程
     * @return
     */
    public ResponseResult ShowCourses();
    public ResponseResult SelectCourse(SelectCourseDto selectCourseDto);
    /**
     * 查询对应科目分数
     * @Param setScoreDto
     * @return
     */
    public ResponseResult GetScore(SetScoreDto setScoreDto);
}
