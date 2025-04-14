package com.iss.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.iss.Dto.UserCourseDto;

import com.iss.Mappers.UserCourseMapper;
import com.iss.Repos.CourseRepository;
import com.iss.Repos.UserCourseRepository;
import com.iss.models.Course;
import com.iss.models.SubscriptionStatus;
import com.iss.models.UserCourse;


@Service
public class UserCourseService {
	private final UserCourseRepository repos;
	private final CourseRepository courserepos;
	public UserCourseService(UserCourseRepository repos,CourseRepository courserepos)
	{
		this.repos=repos;
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
	@Cacheable(
		    value = "userCourses",
		    key = "#id + '_' + #email",
		    unless = "#result == null or #result.subscriptionStatus.name() == 'PENDING'"
		)
		public UserCourseDto findByCourseId(int id, String email) {
		    try {
		        Optional<UserCourse> usercourse = repos.findByCourseIdAndEmail(id, email);
		        
		        if (usercourse.isPresent()) {
		            return UserCourseMapper.Instance.toDto(usercourse.get());
		        } else {
		            Course c = this.courserepos.findById(id).orElseThrow();
		            
		            UserCourse uc = UserCourse.builder()
		                .course(c)
		                .subscriptionStatus(SubscriptionStatus.PENDING)
		                .build();
		                
		            return UserCourseMapper.Instance.toDto(uc);
		        }
		    } catch (Exception ex) {
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
