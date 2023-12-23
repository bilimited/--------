package com.example.springstudy.controller;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShowUserInfoController {
    private UserService userService;

    @Autowired
    public ShowUserInfoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/show")
    public ResponseResult ShowUserInfo(String uid){
        return userService.ShowUserInfo(uid);
    }
}
