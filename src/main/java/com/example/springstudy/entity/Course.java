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
@TableName("course")
public class Course {

    @TableId
    String cno;
    String cname;
    String dept;
    String tno;
    int day;
    int start;
    int end;
    String room;
    String note;
    int capacity;
    int progress;

}
