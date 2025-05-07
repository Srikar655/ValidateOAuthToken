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
	@Mapping(target = "payments", ignore = true)
	@Mapping(target = "usercourse", ignore = true)
	@Mapping(target = "notifications", ignore = true)
	UserDto toDto(User user);
	User toEntity(UserDto userdto);
	@Mapping(target = "payments", ignore = true)
	@Mapping(target = "usercourse", ignore = true)
	@Mapping(target = "notifications", ignore = true)
	List<UserDto> toDtoList(List<User> courses);
	List<User> toEntityList(List<UserDto> coursedtos);
	
}
