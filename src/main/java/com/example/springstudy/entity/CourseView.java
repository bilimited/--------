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
@TableName("course_view")
public class CourseView {

    private long cno;
    private String cname;
    private String dept;
    private String tname;
    private int day;
    private int start;
    private int end;
    private String room;
    private String note;
    private int capacity;
    private int stunumber;
    private int progress;

}
