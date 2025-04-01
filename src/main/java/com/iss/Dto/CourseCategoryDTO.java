package com.iss.Dto;

import java.util.List;


import lombok.Data;

@Data
public class CourseCategoryDTO {
    private int id;
    private String category;
    private List<CourseDto> course;
}
