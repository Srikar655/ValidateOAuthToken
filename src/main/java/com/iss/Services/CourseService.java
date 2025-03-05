package com.iss.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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
            return CourseMapper.Instance.toDto(repos.save(course));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding the course", ex);
        }
    }

    public List<CourseDto> findAll() {
        List<CourseDto> list = null;
        try {
            list = CourseMapper.Instance.toDtoList(repos.findAll());
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
                throw new RuntimeException("Course with id " + id + " not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching the course with id " + id, ex);
        }
    }

    public CourseDto findThumbnail(int id) {
        try {
            CourseDto dto = new CourseDto();
            dto.setCoursethumbnail(repos.findCoursethumbnailById(id));
            dto.setId(id);
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
                throw new RuntimeException("Course with id " + course.getId() + " not found, update failed");
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
