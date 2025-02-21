package com.iss.Services;

import java.util.List;


import org.springframework.stereotype.Service;

import com.iss.Repos.CourseRepository;
import com.iss.models.Course;



import com.iss.Dto.*;
import com.iss.Mappers.CourseMapper;


@Service
public class CourseService {
	private final CourseRepository repos;
	public CourseService(CourseRepository repos)
	{
		this.repos=repos;
	}
	public CourseDto add(Course course)
	{
		return CourseMapper.Instance.toDto(repos.save(course));
	}
	public List<CourseDto> findAll()
	{
		List<CourseDto> list = null;
		try
		{
			list= CourseMapper.Instance.toDtoList(repos.findAll());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	public CourseDto find(int id) {
		
	    Course c = repos.findById(id).get(); // Fetching the Course entity
	    CourseDto dto = CourseMapper.Instance.toDto(c); // Now map to DTO after modification
	    return dto;
	}

	public CourseDto findThumbnail(int id)
	{
		CourseDto dto=new CourseDto();
		 dto.setCoursethumbnail(repos.findCoursethumbnailById(id));
		 dto.setId(id);
		 return dto;
	}
	public CourseDto update(Course course)
	{
		return CourseMapper.Instance.toDto(repos.save(course));
	}
	public void delete(int id)
	{
		 repos.deleteById(id);
	}
}
