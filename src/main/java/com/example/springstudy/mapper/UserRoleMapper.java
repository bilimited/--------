package com.example.springstudy.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springstudy.entity.User_role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@TableName("user_role")
@Mapper
public interface UserRoleMapper extends BaseMapper<User_role> {

}
