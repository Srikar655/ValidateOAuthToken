package com.iss.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;
import com.iss.Dto.CourseDto;
import com.iss.models.Course;

@Mapper
public interface CourseMapper {
	CourseMapper Instance=Mappers.getMapper(CourseMapper.class);
	@Mapping(target = "coursethumbnail", ignore = true)
	@Mapping(target = "vedios", ignore = true)
	CourseDto toDto(Course course);
	Course toEntity(CourseDto coursedto);
	@Mapping(target = "coursethumbnail", ignore = true)
	@Mapping(target = "vedios", ignore = true)
	List<CourseDto> toDtoList(List<Course> courses);
	List<CourseDto> toEntityList(List<CourseDto> coursedtos);
	
}
