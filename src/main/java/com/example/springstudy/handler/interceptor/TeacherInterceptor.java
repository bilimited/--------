package com.example.springstudy.handler.interceptor;

import com.example.springstudy.domain.enums.AppHttpCodeEnum;
import com.example.springstudy.entity.User;
import com.example.springstudy.service.UserService;
import com.example.springstudy.utils.UserThreadLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TeacherInterceptor implements HandlerInterceptor {

    private UserService userService;

    @Autowired
    public TeacherInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = UserThreadLocal.get();
        if(userService.checkRole(user)!="teacher"){
            throw new Exception(String.valueOf(AppHttpCodeEnum.NO_OPERATOR_AUTH));
        }
        return true;
    }
}
