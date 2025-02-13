package com.iss.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import com.iss.Dto.TaskImageDto;
import com.iss.models.TaskImages;

@Mapper
public interface TaskImageMapper {
	TaskImageMapper Instance=Mappers.getMapper(TaskImageMapper.class);
	TaskImageDto toDto(TaskImages task);
	TaskImages toEntity(TaskImageDto dto);
	List<TaskImageDto> toDtoList(List<TaskImages> taskimages);
	List<TaskImages>	toEntityList(List<TaskImageDto> taskimages);
}
