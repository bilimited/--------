package com.example.springstudy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

    @TableId(type = IdType.AUTO)
    long cno;
    String cname;
    String dept;
    long tno;
    int day;
    int start;
    int end;
    String room;
    String note;
    int capacity;
    int progress;

    public Course(String cname, long tno) {
        this.cname = cname;
        this.tno = tno;
    }

}
