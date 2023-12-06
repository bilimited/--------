package com.example.springstudy.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springstudy.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@TableName("student")
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Select("Select * from student where sno=#{sno}")
    public List<Student> getStuByNo(String sno);

}
