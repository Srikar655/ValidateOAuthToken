package com.iss.Mappers;

import com.iss.Dto.CourseCategoryDTO;
import com.iss.models.CourseCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseCategoryMapper {

    @Mapping(target = "course", ignore = true) 
    CourseCategoryDTO toCourseCategoryDTO(CourseCategory courseCategory);

    CourseCategory toCourseCategory(CourseCategoryDTO courseCategoryDTO);
    
    @Mapping(target = "course", ignore = true)
    List<CourseCategoryDTO> toCourseCategoryDTOList(List<CourseCategory> courseCategories);

    List<CourseCategory> toCourseCategoryList(List<CourseCategoryDTO> courseCategoryDTOs);
}
