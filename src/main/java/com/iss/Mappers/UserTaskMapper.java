package com.iss.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;
import com.iss.Dto.UserTaskDto;
import com.iss.models.UserTask;

@Mapper
public interface UserTaskMapper {
	UserTaskMapper Instance=Mappers.getMapper(UserTaskMapper.class);
	@Mapping(target = "uservedio", ignore = true)
	@Mapping(target = "payments", ignore = true)
	@Mapping(target = "task.task", ignore = true)
	@Mapping(target = "task.taskurl", ignore = true)
	@Mapping(target = "task.taskimages", ignore = true)
	@Mapping(target = "task.video", ignore = true)
	@Mapping(target = "task.usertask", ignore = true)
	@Mapping(target = "usersolution", ignore = true)
	UserTaskDto toDto(UserTask task);
	UserTask toEntity(UserTaskDto taskDto);
	@Mapping(target = "uservedio", ignore = true)
	@Mapping(target = "usersolution", ignore = true)
	@Mapping(target = "payments", ignore = true)
	@Mapping(target = "task.task", ignore = true)
	@Mapping(target = "task.taskurl", ignore = true)
	@Mapping(target = "task.taskimages", ignore = true)
	@Mapping(target = "task.video", ignore = true)
	@Mapping(target = "task.usertask", ignore = true)
	List<UserTaskDto> toDtoList(List<UserTask> tasks);
	List<UserTask> toEntityList(List<UserTaskDto> tasks);
	
}