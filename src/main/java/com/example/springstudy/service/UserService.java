package com.example.springstudy.service;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.dto.LoginUserDto;
import com.example.springstudy.entity.dto.LoginUserResponseDto;
import com.example.springstudy.entity.dto.RegistryUserDto;
import com.example.springstudy.entity.User;
import org.springframework.stereotype.Component;

/**
 * 用户服务接口。接口的实现位于./impl/UserServiceImpl
 */

public interface UserService {

    /**
     * 处理用户注册
     * @param registryUserDto 用户发起注册时前端的请求数据
     * @return 通用响应类型
     */
    ResponseResult registryUser(RegistryUserDto registryUserDto);

    /**
     * 处理用户登录
     * @param loginUserDto 用户登录时的请求数据
     * @return 响应类型，其中响应体的数据是LoginUserResponseDto类型的。
     */
    ResponseResult<LoginUserResponseDto> login(LoginUserDto loginUserDto);

    /**
     * 检查用户Token是否正确。如果正确，从token中解析出用户信息并返回
     * @param token
     * @return 从Token中解析出的用户信息
     */
    User checkToken(String token);

    String checkRole(User user);

}
