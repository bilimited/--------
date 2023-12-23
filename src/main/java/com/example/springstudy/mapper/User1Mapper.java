package com.example.springstudy.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springstudy.entity.User1;
import org.apache.ibatis.annotations.Mapper;

@TableName("user")
@Mapper
public interface User1Mapper extends BaseMapper<User1> {
}
