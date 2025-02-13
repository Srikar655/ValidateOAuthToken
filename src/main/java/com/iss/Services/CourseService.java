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
		Course c= repos.save(course);
		return CourseMapper.Instance.toDto(c);
	}
	public List<CourseDto> findAll()
	{
		List<CourseDto> list= CourseMapper.Instance.toDtoList(repos.findAll());
		return list;
	}
	public CourseDto find(int id) {
	    Course c = repos.findById(id).get(); // Fetching the Course entity
	    CourseDto dto = CourseMapper.Instance.toDto(c); // Now map to DTO after modification
	    dto.setCoursethumbnail(c.getCoursethumbnail());
	    return dto;
	}

	public byte[] findThumbnail(int id)
	{
		return repos.findCoursethumbnailById(id);
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
