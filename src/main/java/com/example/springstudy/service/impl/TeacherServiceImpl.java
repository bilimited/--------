package com.example.springstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springstudy.entity.*;
import com.example.springstudy.entity.dto.OpenCourseDto;
import com.example.springstudy.entity.dto.SetCourseProgressDto;
import com.example.springstudy.mapper.CourseViewMapper;
import com.example.springstudy.mapper.StudentMapper;
import com.example.springstudy.mapper.TeacherMapper;
import com.example.springstudy.mapper.UserRoleMapper;
import com.example.springstudy.service.TeacherService;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    UserRoleMapper userRoleMapper;
    TeacherMapper teacherMapper;
    CourseViewMapper courseViewMapper;

    @Override
    public Teacher GetTeacher(User user) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User_role> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("uid",user.getUid());

        String tno = userRoleMapper.selectOne(userRoleQueryWrapper).getSno();
        if(tno==null){
            return null;
        }

        teacherQueryWrapper.eq("tno",tno);
        return teacherMapper.selectOne(teacherQueryWrapper);
    }

    @Override
    public List<Course> GetTeachingCourses(String tno) {
        return null;
    }

    @Override
    public int OpenCourse(OpenCourseDto openCourseDto) {
        return 0;
    }

    @Override
    public int SetCourseProgress(SetCourseProgressDto setCourseProgressDto) {
        return 0;
    }
}
