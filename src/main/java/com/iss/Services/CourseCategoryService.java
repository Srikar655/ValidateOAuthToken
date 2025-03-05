package com.iss.Services;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iss.models.CourseCategory;
import com.iss.Repos.CourseCategoryRepository;

@Service
public class CourseCategoryService {

    private final CourseCategoryRepository courseCategoryRepository;

    public CourseCategoryService(CourseCategoryRepository courseCategoryRepository) {
        this.courseCategoryRepository = courseCategoryRepository;
    }
    public List<CourseCategory> getAllCourseCategories() {
        return courseCategoryRepository.findAll();
    }
    public Optional<CourseCategory> getCourseCategoryById(int id) {
        return courseCategoryRepository.findById(id);
    }
    public CourseCategory saveOrUpdateCourseCategory(CourseCategory courseCategory) {
        return courseCategoryRepository.save(courseCategory);
    }
    public void deleteCourseCategoryById(int id) {
        courseCategoryRepository.deleteById(id);
    }
    public CourseCategory updateCourseCategory( CourseCategory updatedCategory) {
        return courseCategoryRepository.findById(updatedCategory.getId())
            .map(courseCategory -> {
                courseCategory.setCategory(updatedCategory.getCategory());
                courseCategory.setCourse(updatedCategory.getCourse());
                return courseCategoryRepository.save(courseCategory);
            })
            .orElseThrow(() -> new RuntimeException("Course Category not found with ID: " + updatedCategory.getId()));
    }
}
