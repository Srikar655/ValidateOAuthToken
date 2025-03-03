package com.iss.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iss.Dto.UserDto;
import com.iss.Dto.UserTaskDto;
import com.iss.Mappers.UserMapper;
import com.iss.Mappers.UserTaskMapper;
import com.iss.Repos.UserRepository;
import com.iss.Repos.UserTaskRepsitory;
import com.iss.models.User;
import com.iss.models.UserTask;

@Service
public class UserTaskService {
	private UserTaskRepsitory repos;
	public UserTaskService(UserTaskRepsitory repos)
	{
		this.repos=repos;
	}
	public UserTaskDto add(UserTask user)
	{
		try
		{
			return UserTaskMapper.Instance.toDto(repos.save(user));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public List<UserTaskDto> findAll()
	{
		try
		{
			return UserTaskMapper.Instance.toDtoList(repos.findAll());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public UserTaskDto find(int id)
	{
		try
		{
			return UserTaskMapper.Instance.toDto(repos.findById(id).get());
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
