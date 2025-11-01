package com.iss.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;
import com.iss.Dto.UserDto;
import com.iss.models.User;

@Mapper
public interface UserMapper {
	UserMapper Instance=Mappers.getMapper(UserMapper.class);
	UserDto toDto(User user);
	User toEntity(UserDto userdto);
	List<UserDto> toDtoList(List<User> users);
	List<User> toEntityList(List<UserDto> userDtos);
	
}
