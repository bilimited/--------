package com.example.springstudy.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springstudy.entity.Course;
import com.example.springstudy.entity.CourseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 和Course_view完全一样。
 * 所以直接继承Course_view了。
 */

public class OpenCourseDto extends CourseView {


}
