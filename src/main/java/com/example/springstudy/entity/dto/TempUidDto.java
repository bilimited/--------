package com.example.springstudy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 本类只针对那些单个参数传递的情况，用于接收对应的单个参数
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TempUidDto {
    private long uid;


}
