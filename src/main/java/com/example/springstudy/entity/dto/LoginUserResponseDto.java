package com.example.springstudy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户发起登录请求后后端返回给前端的数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponseDto {

    private String accessToken;

}
