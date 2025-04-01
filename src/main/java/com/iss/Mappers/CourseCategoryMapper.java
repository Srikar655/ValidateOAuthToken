package com.iss.Mappers;

import com.iss.Dto.CourseCategoryDTO;
import com.iss.models.CourseCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseCategoryMapper {
	CourseCategoryMapper Instance=Mappers.getMapper(CourseCategoryMapper.class);
    @Mapping(target = "course", ignore = true) 
    CourseCategoryDTO toCourseCategoryDTO(CourseCategory courseCategory);

    CourseCategory toCourseCategory(CourseCategoryDTO courseCategoryDTO);
    
    @Mapping(target = "course", ignore = true)
    List<CourseCategoryDTO> toCourseCategoryDTOList(List<CourseCategory> courseCategories);

    List<CourseCategory> toCourseCategoryList(List<CourseCategoryDTO> courseCategoryDTOs);
}
