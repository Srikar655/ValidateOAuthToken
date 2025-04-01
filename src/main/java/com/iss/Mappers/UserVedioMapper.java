package com.iss.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;
import com.iss.Dto.UserVedioDto;
import com.iss.models.UserVedio;

@Mapper
public interface UserVedioMapper {
	UserVedioMapper Instance=Mappers.getMapper(UserVedioMapper.class);
	@Mapping(target = "usercourse", ignore = true)
	@Mapping(target = "usertask", ignore = true)
	@Mapping(target = "vedio.videourl", ignore = true)
	@Mapping(target = "vedio.course", ignore = true)
	@Mapping(target = "vedio.uservedio", ignore = true)
	@Mapping(target = "vedio.tasks", ignore = true)
	@Mapping(target = "payments", ignore = true)
	UserVedioDto toDto(UserVedio vedio);
	UserVedio toEntity(UserVedioDto uservedio);
	@Mapping(target = "usercourse", ignore = true)
	@Mapping(target = "usertask", ignore = true)
	@Mapping(target = "vedio.videourl", ignore = true)
	@Mapping(target = "vedio.course", ignore = true)
	@Mapping(target = "vedio.tasks", ignore = true)
	@Mapping(target = "vedio.uservedio", ignore = true)
	@Mapping(target = "payments", ignore = true)
	List<UserVedioDto> toDtoList(List<UserVedio> uservedios);
	List<UserVedio> toEntityList(List<UserVedioDto> uservedios);
	
}