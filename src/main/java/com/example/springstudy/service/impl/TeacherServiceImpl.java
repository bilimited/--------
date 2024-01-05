package com.example.springstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.domain.enums.AppHttpCodeEnum;
import com.example.springstudy.entity.*;
import com.example.springstudy.entity.dto.*;
import com.example.springstudy.mapper.*;
import com.example.springstudy.service.TeacherService;
import com.example.springstudy.utils.UserThreadLocal;
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
        long tno = userRoleMapper.selectOne(userRoleQueryWrapper).getTno();
        if(tno==0){
            return null;
        }
        teacherQueryWrapper.eq("tno",tno);
        return teacherMapper.selectOne(teacherQueryWrapper);
    }

    @Override
    public Teacher GetTeacher() {
        return GetTeacher(UserThreadLocal.get());
    }

    // 获得教师所教学的课程
    @Override
    public ResponseResult GetTeachingCourses(long tno) {
        QueryWrapper<CourseView> courseQueryWrapper = new QueryWrapper<>();
//        // 获得当前进程的user对象,此处注释后期或可更改
//        Teacher teacher = GetTeacher(UserThreadLocal.get());
//        String tno = teacher.getTno();
        // 构建查询条件为该tno的所有课程记录
        courseQueryWrapper.eq("tno",tno);
        // 返回根据构建的条件所查询到的所有结果
        return ResponseResult.okResult(courseViewMapper.selectList(courseQueryWrapper));
    }

    /**
     * 该方法已废弃，不要调用这个方法
     * @param openCouDto
     * @return
     */
    @Deprecated
    @Override
    public ResponseResult OpenCourse(OpenCouDto openCouDto) {
        // 将课程添加到数据库中
        Course course = new Course(
        );
        // System.out.println("$$$开始插入$$$");
        // System.out.println(course);
        courseMapper.insert(course);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult OpenCourse(Course course) {
        // 在这个地方对课程进行判断是否选择的时段非法
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("day",course.getDay());
        List<Course> courses = courseMapper.selectList(wrapper);
        if(courses != null){
            // 如果该教师在同一天里面有课程
            int start = course.getStart();
            int end = course.getEnd();
            for(Course cou : courses){
                // 遍历列表里面的课程
                int started = cou.getStart();
                int ended = cou.getEnd();
                if(start>=started && start<ended){
                    System.out.println("--------------jdde");
                    // 如果开课时间在已存在的课程时间内
                    return ResponseResult.errorResult(AppHttpCodeEnum.TIME_CONFLIECT);
                }
                if(start<started && end>started){
                    System.out.println("%%%%%%%%%%%%%%%%%%%");
                    // 如果课程结束时间晚于下一节课
                    return ResponseResult.errorResult(AppHttpCodeEnum.TIME_CONFLIECT);
                }
            }

        }

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
    public ResponseResult GetLearnStudents(long cno) {
        QueryWrapper<Student_course> wrapper = new QueryWrapper<>();
        wrapper.eq("cno",cno);
        // 根据课程号找到课程下所属的学生，应该返回一个列表,列表中存储的是所有选择这个课程的学生的学号、课程号与成绩
        List<Student_course> studentCourse = studentCourseMapper.selectList(wrapper);

        // 用来存储当前课程的学生的信息
        List<ClassStudentDto> studentDtos = new ArrayList<>();
        if (studentCourse != null){

            for(Student_course sc : studentCourse){
                // 根据课程编号找到学生编号
                long sno = sc.getSno();
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
    public ResponseResult SerStudentScore(SetScoreDto ss){
        //for(SetScoreDto ss : setScoreDto){
            // 修改Student_course表格中对应学号与课程号的成绩
            UpdateWrapper<Student_course> wrapper = new UpdateWrapper<>();
            wrapper.eq("sno",ss.getSno())
                    .eq("cno",ss.getCno())
                    .set("semester",ss.getSemester());
            studentCourseMapper.update(null,wrapper);
        //}
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult UpdateCourse(Course newcourse) {
        courseMapper.update(newcourse,new QueryWrapper<Course>().eq("cno",newcourse.getCno()));
        return ResponseResult.okResult();
    }
}
