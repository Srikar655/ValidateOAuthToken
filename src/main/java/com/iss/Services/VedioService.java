package com.iss.Services;


import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iss.Dto.VideoDto;
import com.iss.Mappers.VideoMapper;
import com.iss.Repos.VedioRepository;
import com.iss.models.Vedio;

@Service
public class VedioService {
	private final VedioRepository repos;
	public VedioService(VedioRepository repos)
	{
		this.repos=repos;
	}
	public VideoDto add(VideoDto vedio)
	{
		VideoMapper mapper=VideoMapper.Instance;
		return mapper.toDto(repos.save(mapper.toEntity(vedio)));
	}
	public List<VideoDto> add(List<Vedio> vedios)
	{
		return VideoMapper.Instance.toDtoList(repos.saveAll(vedios));
	}
	public List<VideoDto> findAll()
	{
		return VideoMapper.Instance.toDtoList(repos.findAll());
	}
	public VideoDto find(int id)
	{
		return VideoMapper.Instance.toDto(repos.findById(id).get());
	}
	public List<VideoDto> findByCourseId(int courseId, Pageable pageable) {
	    List<Vedio> list= repos.findByCourseId(courseId, pageable).getContent();
	    //System.out.println(list);
	    return VideoMapper.Instance.toDtoList(list);
	}

	public VideoDto update(Vedio vedio)
	{
		return VideoMapper.Instance.toDto(repos.save(vedio));
	}
	public void delete(int id)
	{
		 repos.deleteById(id);
	}
}
