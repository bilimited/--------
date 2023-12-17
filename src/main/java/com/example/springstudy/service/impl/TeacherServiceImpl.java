package com.example.springstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.*;
import com.example.springstudy.entity.dto.LoginUserResponseDto;
import com.example.springstudy.entity.dto.OpenCouDto;
import com.example.springstudy.entity.dto.OpenCourseDto;
import com.example.springstudy.entity.dto.SetCourseProgressDto;
import com.example.springstudy.mapper.*;
import com.example.springstudy.service.TeacherService;
import com.example.springstudy.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    UserRoleMapper userRoleMapper;
    TeacherMapper teacherMapper;
    CourseViewMapper courseViewMapper;
    CourseMapper courseMapper;

    @Autowired
    public TeacherServiceImpl(UserRoleMapper userRoleMapper, TeacherMapper teacherMapper, CourseViewMapper courseViewMapper, CourseMapper courseMapper) {
        this.userRoleMapper = userRoleMapper;
        this.teacherMapper = teacherMapper;
        this.courseViewMapper = courseViewMapper;
        this.courseMapper = courseMapper;
    }

    // 通过用户对象找到对应的teancher对象
    @Override
    public Teacher GetTeacher(User user) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User_role> userRoleQueryWrapper = new QueryWrapper<>();
        // 根据uid从user_role表中查询数据,此时仅仅构建了查询条件，还没有查询
        userRoleQueryWrapper.eq("uid",user.getUid());
        // 以userRoleQueryWrapper作为条件找到对应的tno并且返回第一条数据
        String tno = userRoleMapper.selectOne(userRoleQueryWrapper).getSno();
        if(tno==null){
            return null;
        }
        teacherQueryWrapper.eq("tno",tno);
        return teacherMapper.selectOne(teacherQueryWrapper);
    }

    // 获得教师所教学的课程
    @Override
    public List<Course> GetTeachingCourses(String tno) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
//        // 获得当前进程的user对象,此处注释后期或可更改
//        Teacher teacher = GetTeacher(UserThreadLocal.get());
//        String tno = teacher.getTno();
        // 构建查询条件为该tno的所有课程记录
        courseQueryWrapper.eq("tno",tno);
        // 返回根据构建的条件所查询到的所有结果
        return courseMapper.selectList(courseQueryWrapper);
    }

    @Override
    public ResponseResult OpenCourse(OpenCouDto openCouDto) {
        // 将课程添加到数据库中
        Course course = new Course(
                openCouDto.getCno(),
                openCouDto.getCname(),
                openCouDto.getTno()
        );
        System.out.println("$$$开始插入$$$");
        System.out.println(course);
        courseMapper.insert(course);
        return ResponseResult.okResult();
    }

    @Override
    public int SetCourseProgress(SetCourseProgressDto setCourseProgressDto) {
        // 获得当前用户对象对应的teacher对象
//        Teacher teacher = GetTeacher(UserThreadLocal.get());
        UpdateWrapper<Course> wrapper = new UpdateWrapper<>();
        wrapper.eq("cno",setCourseProgressDto.getCno())
                .set("progress",setCourseProgressDto.getProgress());
        return courseMapper.update(null,wrapper);
    }
}
