package com.example.springstudy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

    public User(long uid, String username, String password, String salt, String role,String realname) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.role = role;
        this.realname = realname;
        create_time = new Timestamp(System.currentTimeMillis());
        update_time = new Timestamp(System.currentTimeMillis());
    }



    @TableId(type = IdType.AUTO)
    private long uid;
    private String username;
    private String password;
    /*
        当User对象被序列化为JSON字符串时，salt属性将不会被包含在生成的JSON中，
        也不会从JSON字符串中解析为User对象的属性。这样可以在序列化和反序列化过程中保护敏感信息的安全性。
     */
    @JsonIgnore
    private String salt = UUID.randomUUID().toString().replaceAll("-","");
    private String phone;

    // 用户真实姓名，暂时注释掉，
    private String realname;
    private String sex;
    private int age;
    // 用户头像id
    private String portraitid;

    private String role;
    private Timestamp create_time;
    private Timestamp update_time;




}
