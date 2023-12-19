package com.example.springstudy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 教师开课时候所用的数据
// 也用作学生查看课程时候所用的数据
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenCouDto {
    private String cno;
    private String cname;
    private String dept;
    private String tno;
    private int day;
    private int start;
    private int end;
    private String room;
    private String note;
    private int capacity;
    private int progress;


}
