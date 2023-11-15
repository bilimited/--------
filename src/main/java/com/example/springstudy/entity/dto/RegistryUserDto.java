package com.example.springstudy.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;


/**
 * 用户注册时前端发给后端的数据.标注@JsonIgnore的字段为在后端生成，前端不需要赋值
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistryUserDto {
    private long uid;
    private String username;
    private String password;

    /**
     * 盐值：一个随机的字符串，用于登录验证
     */
    @JsonIgnore
    private String salt = UUID.randomUUID().toString().replaceAll("-","");
    private String phone;
    private String role;
    @JsonIgnore
    private Timestamp create_time;
    @JsonIgnore
    private Timestamp update_time;


}
