package com.example.springstudy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
