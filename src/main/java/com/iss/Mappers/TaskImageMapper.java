package com.iss.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


import com.iss.Dto.TaskImageDto;
import com.iss.models.TaskImages;

@Mapper
public interface TaskImageMapper {
	TaskImageMapper Instance=Mappers.getMapper(TaskImageMapper.class);
	@Mapping(target="task",ignore=true)
	TaskImageDto toDto(TaskImages task);
	TaskImages toEntity(TaskImageDto dto);
	@Mapping(target="task",ignore=true)
	List<TaskImageDto> toDtoList(List<TaskImages> taskimages);
	List<TaskImages>	toEntityList(List<TaskImageDto> taskimages);
}
