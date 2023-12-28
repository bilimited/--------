package com.example.springstudy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetScoreDto {
    private long sno;
    private String semester;
    private long cno;

    public SetScoreDto(long sno,long cno){
        this.cno = cno;
        this.sno = sno;
    }
}
