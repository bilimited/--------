package com.example.springstudy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录时，前端向后端发送的数据类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    private String username;
    private String password;


}
