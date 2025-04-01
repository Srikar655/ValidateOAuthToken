package com.iss.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;
import com.iss.Dto.UserTaskSolutionDto;
import com.iss.models.UsersTaskSolution;

@Mapper
public interface UserTaskSolutionMapper {
	UserTaskSolutionMapper Instance=Mappers.getMapper(UserTaskSolutionMapper.class);
	@Mapping(target = "usertask.uservedio", ignore = true)
	@Mapping(target = "usertask.payments", ignore = true)
	@Mapping(target = "usertask.usersolution", ignore = true)
	@Mapping(target = "usertask.task.taskimages", ignore = true)
	@Mapping(target = "usertask.task.video", ignore = true)
	@Mapping(target = "usertask.task.usertask", ignore = true)
	@Mapping(target = "solutionimages", ignore = true)
	UserTaskSolutionDto toDto(UsersTaskSolution usertasksolution);
	UsersTaskSolution toEntity(UserTaskSolutionDto usertasksolutiondto);
	@Mapping(target = "usertask.uservedio", ignore = true)
	@Mapping(target = "usertask.payments", ignore = true)
	@Mapping(target = "usertask.usersolution", ignore = true)
	@Mapping(target = "usertask.task.taskimages", ignore = true)
	@Mapping(target = "usertask.task.video", ignore = true)
	@Mapping(target = "usertask.task.usertask", ignore = true)
	@Mapping(target = "solutionimages", ignore = true)
	List<UserTaskSolutionDto> toDtoList(List<UsersTaskSolution> usertasksolutions);
	List<UsersTaskSolution> toEntityList(List<UserTaskSolutionDto> usertasksolutiondtos);
	
}