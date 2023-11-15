package com.example.springstudy.controller;

import org.springframework.web.bind.annotation.*;

/**
 * 本类没有任何用，仅供测试。
 *
 * RESTful API 设计风格
 * url中不出现动词(GET /user/id/,POST /user/ 代替/getuser?xxx/,/adduser/ ......)
 * (省略具体实现)
 */
@RestController
public class RestAPIController {
    @GetMapping("/user/{id}")
    public String getUserByID(@PathVariable int id){
        return "获取user(id:"+id+")";
    }
    @PostMapping("/user")
    public String saveUser(String user){
        return  "添加user";
    }
    @PutMapping("/user")
    public String updateUser(String user){
        return "更新user";
    }
    @DeleteMapping("/user/{id}")
    public String delUserByID(@PathVariable int id){
        return "删除user(id:"+id+")";
    }

}
