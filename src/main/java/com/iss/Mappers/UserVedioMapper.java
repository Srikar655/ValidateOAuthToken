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
	UserVedioDto toDto(UserVedio vedio);
	UserVedio toEntity(UserVedioDto uservedio);
	@Mapping(target = "usercourse", ignore = true)
	@Mapping(target = "usertask", ignore = true)
	List<UserVedioDto> toDtoList(List<UserVedio> uservedios);
	List<UserVedio> toEntityList(List<UserVedioDto> uservedios);
	
}