package com.example.springstudy.controller;

import com.example.springstudy.domain.ResponseResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * 本类没有任何用，仅供测试。
 *
 *  RestController:直接把数据以文本返回前端，后端不会涉及View的内容
 *  默认把数据转为json格式
 */
@RequestMapping("/hello")
@RestController
public class HelloController {

    @GetMapping("/tokentest")
    public ResponseResult tokentest(){
        return ResponseResult.okResult();
    }

    @GetMapping("/Hello")
    public String helloThere(){
        return "HELLO SPRING!!! CNM";
    }

    /**
     * RequestMapping 第一个参数:用于匹配url映射 可以用*通配 如/getJson/*.json
     * 第二个：请求类型，如GET POST
     */
    @RequestMapping(value = "/HelloJson",method = RequestMethod.GET)
    public ArrayList<String> helloJson(){
        ArrayList<String> s = new ArrayList<String>();
        s.add("a");
        s.add("b");
        s.add("c");
        return s;
    }

    /**
     * url传参 传参方法:/Helloparam?p=www
     * 多个参数:/Url?p1=111&p2=222
     * (Url是value,p1和p2是参数名,111 222是参数内容)
     *
     * @RequestParam 注解代表这个参数是必须的
     */
    @RequestMapping(value = "/Helloparam",method = RequestMethod.GET)
    public String helloParam(@RequestParam("p") String p){
        return "hello,,," + p;
    }

    /**
     * post 参数：在请求体里(也可以放到url里)
     */
    @RequestMapping(value = "/Hellopost",method = RequestMethod.POST)
    public String helloPost(String p){
        return "POST请求:"+p;
    }

    /**
     * 发送的请求参数名要和对象的属性一一对应
     * json格式参数:
     * {
     *     "usrName":"xxx",
     *     "password":"xxx",
     * }
     * (如果接受json数据需要加一个注解@RequestBody)

    @RequestMapping(value = "/Hellopostobj",method = RequestMethod.POST)
    public ArrayList<String> helloPostObj(@RequestBody RegistryUserDto registyUserDto){
        ArrayList<String> a = new ArrayList<>();
        a.add((registyUserDto.getUsername()));
        a.add(registyUserDto.getPassword());
        return a;
    }*/

}
