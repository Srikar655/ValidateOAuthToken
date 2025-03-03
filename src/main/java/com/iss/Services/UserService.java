package com.iss.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iss.Dto.UserDto;
import com.iss.Mappers.UserMapper;
import com.iss.Repos.UserRepository;
import com.iss.models.User;

@Service
public class UserService {
	private UserRepository repos;
	public UserService(UserRepository repos)
	{
		this.repos=repos;
	}
	public UserDto add(User user)
	{
		try
		{
			return UserMapper.Instance.toDto(repos.save(user));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public List<UserDto> findAll()
	{
		try
		{
			return UserMapper.Instance.toDtoList(repos.findAll());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public UserDto find(int id)
	{
		try
		{
			return UserMapper.Instance.toDto(repos.findById(id).get());
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
