package com.example.springstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.domain.enums.AppHttpCodeEnum;
import com.example.springstudy.entity.*;
import com.example.springstudy.entity.dto.*;
import com.example.springstudy.mapper.*;
import com.example.springstudy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    UserRoleMapper userRoleMapper;
    TeacherMapper teacherMapper;
    CourseViewMapper courseViewMapper;
    CourseMapper courseMapper;
    StudentCourseMapper studentCourseMapper;
    StudentMapper studentMapper;
    UserMapper userMapper;

    @Autowired
    public TeacherServiceImpl(UserRoleMapper userRoleMapper, TeacherMapper teacherMapper, CourseViewMapper courseViewMapper, CourseMapper courseMapper, StudentCourseMapper studentCourseMapper, StudentMapper studentMapper, UserMapper userMapper) {
        this.userRoleMapper = userRoleMapper;
        this.teacherMapper = teacherMapper;
        this.courseViewMapper = courseViewMapper;
        this.courseMapper = courseMapper;
        this.studentCourseMapper = studentCourseMapper;
        this.studentMapper = studentMapper;
        this.userMapper = userMapper;
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
    public ResponseResult GetTeachingCourses(String tno) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
//        // 获得当前进程的user对象,此处注释后期或可更改
//        Teacher teacher = GetTeacher(UserThreadLocal.get());
//        String tno = teacher.getTno();
        // 构建查询条件为该tno的所有课程记录
        courseQueryWrapper.eq("tno",tno);
        // 返回根据构建的条件所查询到的所有结果
        return ResponseResult.okResult(courseMapper.selectList(courseQueryWrapper));
    }

    @Override
    public ResponseResult OpenCourse(OpenCouDto openCouDto) {
        // 将课程添加到数据库中
        Course course = new Course(
                openCouDto.getCno(),
                openCouDto.getCname(),
                openCouDto.getTno()
        );
        // System.out.println("$$$开始插入$$$");
        // System.out.println(course);
        courseMapper.insert(course);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult SetCourseProgress(SetCourseProgressDto setCourseProgressDto) {
        // 获得当前用户对象对应的teacher对象
//        Teacher teacher = GetTeacher(UserThreadLocal.get());
        UpdateWrapper<Course> wrapper = new UpdateWrapper<>();
        // 根据cno更改课程进度
        wrapper.eq("cno",setCourseProgressDto.getCno())
                .set("progress",setCourseProgressDto.getProgress());
        return ResponseResult.okResult(courseMapper.update(null,wrapper));
    }

    @Override
    public ResponseResult GetLearnStudents(String cno) {
        QueryWrapper<Student_course> wrapper = new QueryWrapper<>();
        wrapper.eq("cno",cno);
        // 根据课程号找到课程下所属的学生，应该返回一个列表,列表中存储的是所有选择这个课程的学生的学号、课程号与成绩
        List<Student_course> studentCourse = studentCourseMapper.selectList(wrapper);

        // 用来存储当前课程的学生的信息
        List<ClassStudentDto> studentDtos = new ArrayList<>();
        if (studentCourse != null){

            for(Student_course sc : studentCourse){
                // 根据课程编号找到学生编号
                String sno = sc.getSno();
                // 构建查询条件
                QueryWrapper<User_role> wrapper1 = new QueryWrapper<>();
                wrapper1.eq("sno",sno);
                // 在student表里面查询学生姓名
                long uid = (userRoleMapper.selectOne(wrapper1)).getUid();
                QueryWrapper<User> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("uid",uid);
                String sname = (userMapper.selectOne(wrapper2)).getRealname();
                // 将数据塞入列表中
                studentDtos.add(new ClassStudentDto(sno,sname,sc.getSemester()));
            }
        }
        else {
            // 没有找到对应课程编号，一般不会出现这种情况
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return ResponseResult.okResult(studentDtos);
    }

    @Override
    public ResponseResult SerStudentScore(List<SetScoreDto> setScoreDto){
        for(SetScoreDto ss : setScoreDto){
            // 修改Student_course表格中对应学号与课程号的成绩
            UpdateWrapper<Student_course> wrapper = new UpdateWrapper<>();
            wrapper.eq("sno",ss.getSno())
                    .eq("cno",ss.getCno())
                    .set("semester",ss.getSemester());
            studentCourseMapper.update(null,wrapper);
        }
        return ResponseResult.okResult();
    }
}
