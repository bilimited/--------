package com.example.springstudy.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springstudy.entity.Student;
import com.example.springstudy.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@TableName("teacher")
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

    @Select("Select * from teacher where tno=#{tno}")
    public List<Student> getTeacherByNo(String tno);
}
