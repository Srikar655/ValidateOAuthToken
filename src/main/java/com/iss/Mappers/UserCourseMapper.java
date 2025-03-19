package com.iss.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;
import com.iss.Dto.UserCourseDto;
import com.iss.models.UserCourse;

@Mapper
public interface UserCourseMapper {
	UserCourseMapper Instance=Mappers.getMapper(UserCourseMapper.class);
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "course.coursethumbnail", ignore = true)
	@Mapping(target = "course.usercourse", ignore = true)
	@Mapping(target = "course.courseCategory.course", ignore = true)
	@Mapping(target = "course.vedios", ignore = true)
	@Mapping(target = "userVedios", ignore = true)
	UserCourseDto toDto(UserCourse course);
	UserCourse toEntity(UserCourseDto coursedto);
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "course.coursethumbnail", ignore = true)
	@Mapping(target = "course.courseCategory.course", ignore = true)
	@Mapping(target = "course.usercourse", ignore = true)
	@Mapping(target = "course.vedios", ignore = true)
	@Mapping(target = "userVedios", ignore = true)
	List<UserCourseDto> toDtoList(List<UserCourse> courses);
	List<UserCourseDto> toEntityList(List<UserCourseDto> coursedtos);
	
}