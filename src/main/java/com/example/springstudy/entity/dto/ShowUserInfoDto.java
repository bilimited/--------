package com.example.springstudy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowUserInfoDto {
    private long uid;
    private String username;
    private String phone;
    private String realname;
    private String sex;
    private int age;
    private String portraitid;
}
