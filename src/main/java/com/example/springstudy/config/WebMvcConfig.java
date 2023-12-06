package com.example.springstudy.config;

import com.example.springstudy.handler.interceptor.LoginInterceptor;
import com.example.springstudy.handler.interceptor.StudentInterceptor;
import com.example.springstudy.handler.interceptor.TeacherInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 * WebMvcConfigurer配置类其实是Spring内部的一种配置方式，采用JavaBean的形式来代替传统的xml配置文件形式进行针对框架个性化定制，
 * 可以自定义一些Handler，Interceptor，ViewResolver，MessageConverter。(摘自CSDN)
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    public WebMvcConfig(LoginInterceptor loginInterceptor,StudentInterceptor studentInterceptor,TeacherInterceptor teacherInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.studentInterceptor = studentInterceptor;
        this.teacherInterceptor = teacherInterceptor;
    }

    private LoginInterceptor loginInterceptor;
    private StudentInterceptor studentInterceptor;
    private TeacherInterceptor teacherInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /**
         * 配置跨域请求
         */
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowCredentials(true) //允许发送Cookie
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 使用拦截器
         */
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**");//参数是需要做登录验证的接口，这里代表验证所有/开头的接口。
        registry.addInterceptor(studentInterceptor)
                .addPathPatterns("/student/**");
        registry.addInterceptor(teacherInterceptor)
                .addPathPatterns("/teacher/**");
    }
}
