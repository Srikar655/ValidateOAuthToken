package com.iss.Mappers;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.iss.Dto.VideoDto;
import com.iss.Dto.VideoRegisterDto;
import com.iss.models.Vedio;

@Mapper
public interface VideoMapper {
	VideoMapper Instance=Mappers.getMapper(VideoMapper.class);
	@Mapping(target = "course.coursethumbnail", ignore = true)
	VideoDto toDto(Vedio video);
	Vedio toEntity(VideoDto videoDto);
	Vedio toEntity(VideoRegisterDto videoDto);
	List<VideoDto> toDtoList(List<Vedio> vedios);
	List<Vedio> toEntityList(List<VideoDto> vedioDtos);
}
