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

        // 这里事先需要往UserThreadLocal这个工具类里面添加对象（put方法），
        // 然后可以用这个工具类的get方法获得当前线程的user对象
        // 在LoginInterceptor中已经将当前的User对象通过put方法加入了，所以这里可以直接使用get()方法获得对应的对象
         User user = UserThreadLocal.get();
        if(userService.checkRole(user)!="teacher"){
            throw new Exception(String.valueOf(AppHttpCodeEnum.NO_OPERATOR_AUTH));
        }
        return true;
    }
}
