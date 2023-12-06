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

    private long uid;
    private String sno;
    private String tno;

}
