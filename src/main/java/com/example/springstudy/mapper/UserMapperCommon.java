package com.example.springstudy.mapper;

import com.example.springstudy.entity.dto.RegistryUserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 本类没有任何用，仅供测试。
 * 正常的Mapper
 * Mapper:用于连接数据库与代码
 */
@Mapper
public interface UserMapperCommon {

    @Select("select * from user")
    public List<RegistryUserDto> find();

    //@Select("insert into user values(#{id},#{usrName},#{password})")
    @Select("insert into user")
    public int insert(RegistryUserDto registryUserDto);

}
