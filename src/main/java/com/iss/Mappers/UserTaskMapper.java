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
	UserTaskDto toDto(UserTask task);
	UserTask toEntity(UserTaskDto taskDto);
	@Mapping(target = "uservedio", ignore = true)
	List<UserTaskDto> toDtoList(List<UserTask> tasks);
	List<UserTask> toEntityList(List<UserTaskDto> tasks);
	
}