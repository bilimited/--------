package com.example.springstudy.controller;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.dto.LoginUserDto;
import com.example.springstudy.entity.dto.RegistryUserDto;
import com.example.springstudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关的Controller。负责所有/user开头的接口
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    //AutoWired:不用手动初始化userService了。
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registry")
    public ResponseResult registryUser(@RequestBody RegistryUserDto registryUserDto){
        return userService.registryUser(registryUserDto);
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginUserDto loginUserDto){
        return userService.login(loginUserDto);
    }

}
