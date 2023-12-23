package com.example.springstudy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteInfoDto {
    private String uid;
    private String phone;
    private String sex;
    private String age;
    private String portraitid;
}
