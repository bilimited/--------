package com.example.springstudy.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.domain.enums.AppHttpCodeEnum;
import com.example.springstudy.entity.User_role;
import com.example.springstudy.entity.dto.LoginUserDto;
import com.example.springstudy.entity.dto.LoginUserResponseDto;
import com.example.springstudy.entity.dto.RegistryUserDto;
import com.example.springstudy.entity.User;
import com.example.springstudy.mapper.StudentMapper;
import com.example.springstudy.mapper.TeacherMapper;
import com.example.springstudy.mapper.UserMapper;
import com.example.springstudy.mapper.UserRoleMapper;
import com.example.springstudy.service.UserService;
import com.example.springstudy.utils.BeanCopyUtil;
import com.example.springstudy.utils.JwtUtils;
import com.example.springstudy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private StudentMapper studentMapper;
    private TeacherMapper teacherMapper;
    private UserRoleMapper roleMapper;
    private RedisCache redisCache;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, StudentMapper studentMapper, TeacherMapper teacherMapper, UserRoleMapper roleMapper, RedisCache redisCache) {
        this.userMapper = userMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.roleMapper = roleMapper;
        this.redisCache = redisCache;
    }

    //用户注册
    @Override
    public ResponseResult registryUser(RegistryUserDto registryUserDto) {

        String password = registryUserDto.getPassword();
        String salt = registryUserDto.getSalt();

        //对密码进行md5加密:原始密码+盐转换成md5.
        String md5Password = DigestUtils.md5DigestAsHex((password+registryUserDto.getSalt()).getBytes());

        registryUserDto.setPassword(md5Password);

        //registryUserDto.setCreate_time(new Timestamp(System.currentTimeMillis()));
        //registryUserDto.setUpdate_time(new Timestamp(System.currentTimeMillis()));

        if(isUserExist(registryUserDto.getUsername())){
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
        }

        //根据注册信息复制出来一个新的User
        User user = new User(
                registryUserDto.getUid(),
                registryUserDto.getUsername(),
                registryUserDto.getPassword(),
                registryUserDto.getSalt(),
                registryUserDto.getRole()
        );
        //User user = BeanCopyUtil.copyBean(registryUserDto,User.class);



        //角色验证部分 依托答辩
        if(user.getRole().equals("student")){
            if(isStudentRegistered(registryUserDto.getNo())){
                return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_REGISTERED);
            }
            if(isStudentExist(registryUserDto.getNo())){
                userMapper.insert(user);
                roleMapper.insert(new User_role(user.getUid(),registryUserDto.getNo(),null));
            }else {
                return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST);
            }
        }else if(user.getRole().equals("teacher")){
            if(isTeacherRegistered(registryUserDto.getNo())){
                return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_REGISTERED);
            }
            if(isTeacherExist(registryUserDto.getNo())){
                userMapper.insert(user);
                roleMapper.insert(new User_role(user.getUid(),null, registryUserDto.getNo()));
            }else {
                return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST);
            }
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST);
        }

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<LoginUserResponseDto> login(LoginUserDto loginUserDto) {
        String usrName = loginUserDto.getUsername();
        String psw = loginUserDto.getPassword();

        //选择一个用户名相同的User
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUsername,usrName);
        User user = userMapper.selectOne(queryWrapper);
        if(null == user) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        String role = checkRole(user);
        if(role==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST);
        }

        String md5psw = DigestUtils.md5DigestAsHex((psw+user.getSalt()).getBytes());
        if(!md5psw.equals(user.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }

        String token = JwtUtils.createToken(user.getUid());//创建一个token
        //将token插入redis,1天后过期
        redisCache.setCacheObject("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);

        return ResponseResult.okResult(new LoginUserResponseDto(token,role));
    }

    /**
     * 验证token的逻辑
     * @param token
     * @return
     */
    @Override
    public User checkToken(String token) {
        System.out.println("check token:"+token);
        if(StringUtils.isEmpty(token)){
            return null;
        }
        Map<String,Object> map = JwtUtils.checkToken(token);
        if(map==null){
            return null;
        }
        String userJson =  redisCache.getCacheObject("TOKEN_" + token);
        if (StringUtils.isEmpty(userJson)) {
            return null;
        }
        User user = JSON.parseObject(userJson, User.class);
        return user;
    }

    public String checkRole(User user){
        QueryWrapper<User_role> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",user.getUid());
        User_role l = roleMapper.selectOne(wrapper);
        if(l!=null){
            return user.getRole();
        }else {
            return null;
        }
    }

    public boolean isStudentExist(String sno){
        return !studentMapper.getStuByNo(sno).isEmpty();
    }
    public boolean isStudentRegistered(String sno){
        QueryWrapper<User_role> wrapper = new QueryWrapper<>();
        wrapper.eq("sno",sno);
        List<User_role> l = roleMapper.selectList(wrapper);
        return  !l.isEmpty();
    }
    public boolean isTeacherExist(String tno){
        return !teacherMapper.getTeacherByNo(tno).isEmpty();
    }
    public boolean isTeacherRegistered(String tno){
        QueryWrapper<User_role> wrapper = new QueryWrapper<>();
        wrapper.eq("tno",tno);
        List<User_role> l = roleMapper.selectList(wrapper);
        return  !l.isEmpty();
    }

    public boolean isUserExist(String username){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        List<User> l = userMapper.selectList(wrapper);
        return  !l.isEmpty();
    }
}
