package com.example.springstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.domain.enums.AppHttpCodeEnum;
import com.example.springstudy.entity.*;
import com.example.springstudy.entity.dto.SelectCourseDto;
import com.example.springstudy.mapper.CourseViewMapper;
import com.example.springstudy.mapper.StudentCourseMapper;
import com.example.springstudy.mapper.StudentMapper;
import com.example.springstudy.mapper.UserRoleMapper;
import com.example.springstudy.service.StudentService;
import com.example.springstudy.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    UserRoleMapper userRoleMapper;
    StudentMapper studentMapper;
    CourseViewMapper courseViewMapper;
    StudentCourseMapper studentCourseMapper;

    @Autowired
    public StudentServiceImpl(UserRoleMapper userRoleMapper, StudentMapper studentMapper, CourseViewMapper courseViewMapper, StudentCourseMapper studentCourseMapper) {
        this.userRoleMapper = userRoleMapper;
        this.studentMapper = studentMapper;
        this.courseViewMapper = courseViewMapper;
        this.studentCourseMapper = studentCourseMapper;
    }

    @Override
    public Student GetStudent(User user) {

        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User_role> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("uid",user.getUid());

        long sno = userRoleMapper.selectOne(userRoleQueryWrapper).getSno();
        if(sno==0){
            return null;
        }
        // 根据sno找到对应的学生对象
        studentQueryWrapper.eq("sno",sno);
        return studentMapper.selectOne(studentQueryWrapper);
    }

    public Student GetStudent(){
        return GetStudent(UserThreadLocal.get());
    }

    @Override
    public ResponseResult GetLearningCourses(long uid) {

        // 可尝试的优化代码
//        Student student = GetStudent(UserThreadLocal.get());
//        String sno = student.getSno();

        // 下面三行根据uid获得学生的学号
        QueryWrapper<User_role> user_roleQueryWrapper = new QueryWrapper<>();
        System.out.println("uid = " + uid);
        user_roleQueryWrapper.eq("uid",uid);
        long sno = userRoleMapper.selectOne(user_roleQueryWrapper).getSno();

        return ResponseResult.okResult(courseViewMapper.GetCoursesBySno(sno));
    }

    @Override
    public ResponseResult ShowCourses() {

        return ResponseResult.okResult(courseViewMapper.selectList(null));
    }

    @Override
    public ResponseResult SelectCourse(SelectCourseDto selectCourseDto) {
        long sno = GetStudent().getSno();
        long cno = selectCourseDto.getCno();
        CourseView course = courseViewMapper.selectOne(new QueryWrapper<CourseView>().eq("cno",cno));
        if(course==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.COURSE_NOT_EXIST);
        }

        // 将这条选课记录插入到student_course表格中去
        int ret = studentCourseMapper.insert(new Student_course(sno,cno,0));
        return ResponseResult.okResult(ret);
    }
}
