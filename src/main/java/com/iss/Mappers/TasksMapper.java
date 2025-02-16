package com.iss.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.iss.Dto.TasksDto;
import com.iss.models.Tasks;

@Mapper
public interface TasksMapper {
	TasksMapper Instance=Mappers.getMapper(TasksMapper.class);
    @Mapping(target = "taskimage", ignore = true)
    @Mapping(target = "video", ignore = true)
	TasksDto toDto(Tasks task);
	Tasks toEntity(TasksDto task);
	@Mapping(target = "taskimage", ignore = true)
	 @Mapping(target = "video", ignore = true)
	List<TasksDto> toDtoList(List<Tasks> tasks);
	List<Tasks> toEntityList(List<TasksDto> taskDtos);
}
