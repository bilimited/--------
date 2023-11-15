package com.example.springstudy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * 用户类，包含用户的各种信息。
 * 与数据库中的user表相对应。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)    //允许链式调用。User.setId().setUserName()......
@TableName("user")
public class User {

    @TableId
    private long uid;
    private String username;
    private String password;
    @JsonIgnore
    private String salt = UUID.randomUUID().toString().replaceAll("-","");
    private String phone;
    private String role;
    private Timestamp create_time;
    private Timestamp update_time;


}
