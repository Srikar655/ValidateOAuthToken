package com.iss.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.iss.Repos.CourseRepository;
import com.iss.models.Course;
import com.iss.Dto.*;
import com.iss.Mappers.CourseMapper;

@Service
public class CourseService {
    private final CourseRepository repos;

    public CourseService(CourseRepository repos) {
        this.repos = repos;
    }

    public CourseDto add(Course course) {
        try {
        	Course c=repos.save(course);
            return CourseMapper.Instance.toDto(c);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding the course", ex);
        }
    }
    public double getCoursePrice(int id) throws Exception
    {
    	try
    	{
    		Optional<Double> optionalprice=repos.getCoursePriceByCourseId(id);
    		if(optionalprice.isPresent())
    		{
    			return repos.getCoursePriceByCourseId(id).get();
    		}
    		return 0;
    	}catch(Exception ex)
    	{
    		ex.printStackTrace();
    		throw new Exception("Error There is No Course Available");
    	}
    }
    @Cacheable(value = "courses")
    public List<CourseDto> findAll() {
        List<CourseDto> list = null;
        try {
            List<Course> courselist = repos.findAll();
            if(courselist!=null)
            {
            	list=CourseMapper.Instance.toDtoList(courselist);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching all courses", ex);
        }
        return list;
    }

    public CourseDto find(int id) {
        try {
            Optional<Course> courseOpt = repos.findById(id);
            if (courseOpt.isPresent()) {
                return CourseMapper.Instance.toDto(courseOpt.get());
            } else {
               return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching the course with id " + id, ex);
        }
    }
    public Course findAndGetCourse(int id) {
        try {
            Optional<Course> courseOpt = repos.findById(id);
            if (courseOpt.isPresent()) {
                return courseOpt.get();
            } else {
               return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching the course with id " + id, ex);
        }
    }
    @Cacheable(value = "coursethumbnail", key = "#id")
    public CourseDto findThumbnail(int id) {
        try {
        	Optional<byte[]> optionalbytes=repos.findCoursethumbnailById(id);
        	CourseDto dto=null;
        	if(optionalbytes.isPresent())
        	{
        			dto = new CourseDto();
        			dto.setCoursethumbnail(optionalbytes.get());
        			dto.setId(id);
        	}
            return dto;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching the course thumbnail with id " + id, ex);
        }
    }

    public CourseDto update(Course course) {
        try {
            Optional<Course> existingCourse = repos.findById(course.getId());
            if (existingCourse.isPresent()) {
            	Course c=existingCourse.get();
            	BeanUtils.copyProperties(course,c ,"usercourse","vedios");
                return CourseMapper.Instance.toDto(repos.save(c));
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error updating the course with id " + course.getId(), ex);
        }
    }

    public void delete(int id) {
        try {
            if (repos.existsById(id)) {
                repos.deleteById(id);
            } else {
                throw new RuntimeException("Course with id " + id + " not found, delete failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error deleting the course with id " + id, ex);
        }
    }
}
