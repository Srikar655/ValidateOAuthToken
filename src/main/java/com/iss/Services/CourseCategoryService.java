package com.iss.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.iss.Dto.CourseCategoryDTO;
import com.iss.Mappers.CourseCategoryMapper;
import com.iss.Repos.CourseCategoryRepository;
import com.iss.models.CourseCategory;

@Service
public class CourseCategoryService  {

    private final CourseCategoryRepository courseCategoryRepository;

    public CourseCategoryService(CourseCategoryRepository courseCategoryRepository) {
        this.courseCategoryRepository = courseCategoryRepository;
    }
    @Cacheable("courseCategories")
    public List<CourseCategory> getAllCourseCategories() {
        try {
            return courseCategoryRepository.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching all course categories", ex);
        }
    }
    @Cacheable(value = "courseCategories", key = "#id")
    public CourseCategory getCourseCategoryById(int id) {
        try {
        	Optional<CourseCategory> course= courseCategoryRepository.findById(id);
        	return course.get();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching course category with id " + id, ex);
        }
    }
    @Caching(evict = {
            @CacheEvict(value = "courseCategories", allEntries = true), // Evict the entire list
            @CacheEvict(value = "courseCategories", key = "#courseCategory.id") // Evict the specific item
        })
    public CourseCategoryDTO saveOrUpdateCourseCategory(CourseCategory courseCategory) {
        try {
        	System.out.println(courseCategory);
            CourseCategoryDTO dto= CourseCategoryMapper.Instance.toCourseCategoryDTO(courseCategoryRepository.save(courseCategory));
            System.out.println(dto);
            return dto;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error saving or updating course category", ex);
        }
    }
    @Caching(evict = {
            @CacheEvict(value = "courseCategories", allEntries = true), // Evict the entire list
            @CacheEvict(value = "courseCategories", key = "#courseCategory.id") // Evict the specific item
        })
    public void deleteCourseCategoryById(int id) {
        try {
            if (courseCategoryRepository.existsById(id)) {
                courseCategoryRepository.deleteById(id);
            } else {
                throw new RuntimeException("Course category with id " + id + " not found, delete failed.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error deleting course category with id " + id, ex);
        }
    }
    @Caching(evict = {
            @CacheEvict(value = "courseCategories", allEntries = true), // Evict the entire list
            @CacheEvict(value = "courseCategories", key = "#courseCategory.id") // Evict the specific item
        })
    public CourseCategory updateCourseCategory(CourseCategory updatedCategory) {
        try {
            Optional<CourseCategory> existingCategoryOpt = courseCategoryRepository.findById(updatedCategory.getId());
            if (existingCategoryOpt.isPresent()) {
                CourseCategory existingCategory = existingCategoryOpt.get();
                existingCategory.setCategory(updatedCategory.getCategory());
                existingCategory.setCourse(updatedCategory.getCourse());
                return courseCategoryRepository.save(existingCategory);
            } else {
                throw new RuntimeException("Course category not found with ID: " + updatedCategory.getId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error updating course category with id " + updatedCategory.getId(), ex);
        }
    }
}
