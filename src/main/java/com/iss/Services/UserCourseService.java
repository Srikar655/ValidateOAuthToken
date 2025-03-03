package com.iss.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iss.Dto.UserCourseDto;
import com.iss.Dto.UserDto;
import com.iss.Mappers.UserCourseMapper;
import com.iss.Mappers.UserMapper;
import com.iss.Repos.UserCourseRepository;
import com.iss.Repos.UserRepository;
import com.iss.models.User;
import com.iss.models.UserCourse;

@Service
public class UserCourseService {
	private UserCourseRepository repos;
	public UserCourseService(UserCourseRepository repos)
	{
		this.repos=repos;
	}
	public UserCourseDto add(UserCourse user)
	{
		try
		{
			return UserCourseMapper.Instance.toDto(repos.save(user));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public List<UserCourseDto> findAll()
	{
		try
		{
			return UserCourseMapper.Instance.toDtoList(repos.findAll());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public UserCourseDto find(int id)
	{
		try
		{
			return UserCourseMapper.Instance.toDto(repos.findById(id).get());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public void delete(int id)
	{
		try
		{
			repos.deleteById(id);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
