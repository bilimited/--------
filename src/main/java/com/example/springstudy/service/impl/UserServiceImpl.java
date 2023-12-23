package com.example.springstudy.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.domain.enums.AppHttpCodeEnum;
import com.example.springstudy.entity.Student;
import com.example.springstudy.entity.Teacher;
import com.example.springstudy.entity.User_role;
import com.example.springstudy.entity.dto.CompleteInfoDto;
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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
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

        registryUserDto.setCreate_time(new Timestamp(System.currentTimeMillis()));
        registryUserDto.setUpdate_time(new Timestamp(System.currentTimeMillis()));

        // 判断用户名是否已经存在
        if(isUserExist(registryUserDto.getUsername())){
            System.out.println("用户名已经存在");
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
        }


        //根据注册信息复制出来一个新的User
        User user = new User(
                registryUserDto.getUid(),
                registryUserDto.getUsername(),
                registryUserDto.getPassword(),
                registryUserDto.getSalt(),
                registryUserDto.getRole(),
                registryUserDto.getRealname()
        );
        //User user = BeanCopyUtil.copyBean(registryUserDto,User.class);


        System.out.println("$$$开始加入数据$$$");
        //角色验证部分 根据填写的role的不同对用户做出区分
        if(user.getRole().equals("student")){
            // 学生部分
            if(isStudentRegistered(registryUserDto.getNo())){
                return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_REGISTERED);
            }
            if(isStudentExist(registryUserDto.getNo())){
                userMapper.insert(user);
                studentMapper.insert(new Student(registryUserDto.getNo()));
                roleMapper.insert(new User_role(user.getUid(),registryUserDto.getNo(),null));
            }else {
                return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST);
            }
        }
        else if(user.getRole().equals("teacher")){
            // 老师部分
            if(isTeacherRegistered(registryUserDto.getNo())){
                return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_REGISTERED);
            }
//            System.out.println("$$$判断没有注册成功$$$");
            // 开始往数据库里面添加数据
            if(isTeacherExist(registryUserDto.getNo())){
//                System.out.println("加入的老师为:" + user.getUsername());
                userMapper.insert(user);
                teacherMapper.insert(new Teacher(registryUserDto.getNo()));
                roleMapper.insert(new User_role(user.getUid(),null, registryUserDto.getNo()));
            }else {
                return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST);
            }
        }
        else{
            return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST);
        }


        System.out.println("$$$运行成功$$$");
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<LoginUserResponseDto> login(LoginUserDto loginUserDto) {
        String usrName = loginUserDto.getUsername();
        String psw = loginUserDto.getPassword();
        // User::getUsername指定了要查询的字段（从数据库中查找的字段），usrName是要查询的用户名。
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUsername,usrName);
        // 从数据库中查找到该用户的信息，此时user内存储该用户存储在数据库中的信息
        User user = userMapper.selectOne(queryWrapper);
        if(user == null) {
            // 用户不存在
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        // 如果这个用户的uid存在，根据uid返回这个用户的role
        String role = checkRole(user);
        if(role==null){
            // 确保role存在
            return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_NOT_EXIST);
        }

        String md5psw = DigestUtils.md5DigestAsHex((psw+user.getSalt()).getBytes());
        if(!md5psw.equals(user.getPassword())){
            // 密码验证没有成功
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }

        System.out.println("$$$登录验证成功$$$");

        // 暂时不管这个东西
        String token = JwtUtils.createToken(user.getUid());//创建一个token
        //将token插入redis,1天后过期
        redisCache.setCacheObject("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);

        System.out.println("$$$登录验证成功并且返回$$$");
        return ResponseResult.okResult(new LoginUserResponseDto(token,role));
    }

    @Override
    public ResponseResult CompleteInfo(CompleteInfoDto completeInfoDto){
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("uid",completeInfoDto.getUid())
                .set("phone",completeInfoDto.getPhone())
                .set("age",completeInfoDto.getAge())
                .set("sex",completeInfoDto.getSex())
                .set("portraitid",completeInfoDto.getPortraitid())
                .set("update_time",new Timestamp(System.currentTimeMillis()));
        if(userMapper.update(null,wrapper) != 0){
            return ResponseResult.okResult();
        }
        else {
            // 其实不可能出现错误，但是以防万一
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

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
        // 通过eq方法设置查询条件，查询uid字段等于user.getUid()的记录
        wrapper.eq("uid",user.getUid());
        // 从ur库中验证是否存在对应的值
        User_role l = roleMapper.selectOne(wrapper);
        if(l!=null){
            // 成功就返回这个用户的role
            return user.getRole();
        }else {
            return null;
        }
    }

    public boolean isStudentExist(String sno){
        return studentMapper.getStuByNo(sno).isEmpty();
    }
    public boolean isStudentRegistered(String sno){
        QueryWrapper<User_role> wrapper = new QueryWrapper<>();
        wrapper.eq("sno",sno);
        List<User_role> l = roleMapper.selectList(wrapper);
        return  !l.isEmpty();
    }
    public boolean isTeacherExist(String tno){
        return teacherMapper.getTeacherByNo(tno).isEmpty();
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
        System.out.println("star jud if exist name");
        List<User> l = userMapper.selectList(wrapper);
        return !l.isEmpty();
//        return false;
    }
}
