package com.iss.Services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.iss.Dto.UserCourseDto;

import com.iss.Mappers.UserCourseMapper;
import com.iss.Repos.CourseRepository;
import com.iss.Repos.UserCourseRepository;
import com.iss.Repos.UserRepository;
import com.iss.models.Course;
import com.iss.models.User;
import com.iss.models.UserCourse;
import com.iss.models.UserCourse.SubscriptionStatus;

@Service
public class UserCourseService {
	private final UserCourseRepository repos;
	private final CourseRepository courserepos;
	private final UserRepository userrepos;
	public UserCourseService(UserCourseRepository repos,CourseRepository courserepos,UserRepository userrepos)
	{
		this.repos=repos;
		this.userrepos=userrepos;
		this.courserepos=courserepos;
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
	public UserCourseDto findByCourseId(int id,String email)
	{
		try
		{
			return UserCourseMapper.Instance.toDto(repos.findByCourseId(id).get());
		}
		catch(NoSuchElementException ex)
		{
			Course c = this.courserepos.findById(id).get();
			User user=userrepos.findByUsernameOrEmail(email, email).get();
			UserCourse uc=UserCourse.builder().course(c).user(user).subscriptionStatus(SubscriptionStatus.PENDING).build();
			return UserCourseMapper.Instance.toDto(uc);
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
