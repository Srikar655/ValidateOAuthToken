package com.iss.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.iss.Dto.UserCourseDisplayDto;
import com.iss.Dto.UserCourseDto;

import com.iss.Mappers.UserCourseMapper;
import com.iss.Repos.CourseRepository;
import com.iss.Repos.UserCourseRepository;
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
	@CacheEvict(value = "userCourseDisplay", key = "#userCourse.course.id + '_' + #userCourse.user.email")
    @Transactional
	public UserCourse add(UserCourse userCourse)
	{
		try
		{
			return repos.save(userCourse);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	@Caching(
	        put = {
	            @CachePut(value = "userCourse", key = "#userCourse.id"),
	            @CachePut(value = "userCourseDisplay", key = "#userCourse.course.id + '_' + #userCourse.user.email")
	        }
	    )
	    @Transactional
	    public UserCourseDto update(UserCourse userCourse) throws NotFoundException {
	        if (!repos.existsById(userCourse.getId())) {
	            throw new NotFoundException();
	        }
	        UserCourse updated = repos.save(userCourse);
	        return UserCourseMapper.Instance.toDto(updated);
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
	@Cacheable(value = "userCourse", key = "#id")
    @Transactional(readOnly = true)
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
		    value = "userCourseDisplay",
		    key = "#id + '_' + #email",
		    unless = "#result == null or #result.subscriptionStatus.name() == 'PENDING'"
		)
		public UserCourseDisplayDto findByCourseId(int id, String email) {
			try {
		        Optional<UserCourseDisplayDto> userCourseOpt = repos.findByCourseIdAndEmail(id, email);
		        
		        UserCourseDisplayDto baseDto;
		        if (userCourseOpt.isPresent()) {
		            baseDto = userCourseOpt.get();
		        } else {
		            baseDto = courserepos.findByCourseIdForUserDisplay(id)
		                .orElse(null); 
		            
		        }
		        
		        if (baseDto == null) {
		            return null;
		        }
	
		        List<String> features = courserepos.findCourseFeaturesById(id);
	
		        baseDto.setCourseFeatures(features);
		        
		        return baseDto;
	
		    } catch (Exception ex) {
		        ex.printStackTrace();
		        throw new RuntimeException("Error assembling course details for course id " + id, ex);
		        //return null;
		    }
		}

	@Caching(
	        evict = {
	            @CacheEvict(value = "userCourse", key = "#id"),
	            @CacheEvict(value = "userCourseDisplay", key = "#result.course.id + '_' + #result.user.email")
	        }
    )
    @Transactional
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
	
	public boolean existsByCourseIdAndUserEmailAndSubscriptionStatus(
	        int courseId, 
	        String userEmail, 
	        SubscriptionStatus subscriptionStatus
	    )
	{
		return this.repos.existsByCourseIdAndUserEmailAndSubscriptionStatus(courseId, userEmail, subscriptionStatus);
	}

}
