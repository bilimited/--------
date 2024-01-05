package com.example.springstudy.service;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.Course;
import com.example.springstudy.entity.Teacher;
import com.example.springstudy.entity.User;
import com.example.springstudy.entity.dto.OpenCouDto;
import com.example.springstudy.entity.dto.OpenCourseDto;
import com.example.springstudy.entity.dto.SetCourseProgressDto;
import com.example.springstudy.entity.dto.SetScoreDto;

import java.util.List;

public interface TeacherService {
    /**
     * 根据user对象获取对应的Teacher对象。
     * @param user
     * @return
     */
    public Teacher GetTeacher(User user);

    public Teacher GetTeacher();

    /**
     * 获取该教师正在教授的课程。
     * @param tno
     * @return
     */
    public ResponseResult GetTeachingCourses(long tno);

    /**
     * 开课
     * @param openCouDto
     * @return
     */
    @Deprecated
    public ResponseResult OpenCourse(OpenCouDto openCouDto);
    public ResponseResult OpenCourse(Course course);
    /**
     * 设置课程进度
     * @param setCourseProgressDto
     * @return
     */
    public ResponseResult SetCourseProgress(SetCourseProgressDto setCourseProgressDto);

    /**
     * 获得对应课程教学的学生
     * @Param cno
     * @return
     */
    public ResponseResult GetLearnStudents(long cno);

    /**
     * 设置课程下学生的分数
     * @Param setScoreDto
     * @return
     */
    public ResponseResult SerStudentScore(SetScoreDto setScoreDto);

    /**
     * 更新课程
     * @param newcourse
     * @return
     */
    public ResponseResult UpdateCourse(Course newcourse);
}
