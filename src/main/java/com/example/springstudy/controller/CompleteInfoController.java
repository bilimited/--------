package com.example.springstudy.controller;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.dto.CompleteInfoDto;
import com.example.springstudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompleteInfoController {
    private UserService userService;

    @Autowired
    public CompleteInfoController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/Completeinfo")
    public ResponseResult CompleteInfo(@RequestBody CompleteInfoDto completeInfoDto){
        return userService.CompleteInfo(completeInfoDto);
    }
}
