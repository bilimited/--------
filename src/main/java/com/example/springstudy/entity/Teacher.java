package com.example.springstudy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)    //允许链式调用。User.setId().setUserName()......
@TableName("teacher")
public class Teacher {
    @TableId
    private String tno;
    private String tname;
    private String college;
    private String major;
    private String sex;

    public Teacher(String tno,String tname){
        this.tno = tno;
        this.tname = tname;
    }

}
