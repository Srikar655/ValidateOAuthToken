package com.iss.Services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.iss.Dto.UserDto;
import com.iss.Dto.UserVedioDto;
import com.iss.Mappers.UserMapper;
import com.iss.Mappers.UserVedioMapper;
import com.iss.Repos.UserRepository;
import com.iss.Repos.UserVideosRepository;
import com.iss.models.User;
import com.iss.models.UserVedio;

@Service
public class UserVedioService {
	private UserVideosRepository repos;
	public UserVedioService(UserVideosRepository repos)
	{
		this.repos=repos;
	}
	public UserVedioDto add(UserVedio user)
	{
		try
		{
			return UserVedioMapper.Instance.toDto(repos.save(user));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public List<UserVedioDto> findAll()
	{
		try
		{
			return UserVedioMapper.Instance.toDtoList(repos.findAll());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public UserVedioDto find(int id)
	{
		try
		{
			return UserVedioMapper.Instance.toDto(repos.findById(id).get());
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

