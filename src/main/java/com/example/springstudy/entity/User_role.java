package com.example.springstudy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)    //允许链式调用。User.setId().setUserName()......
@TableName("user_role")
public class User_role {

    // 这里Long是因为sno与tno有的要设置为null
    private long uid;
    private Long sno;
    private Long tno;


}
