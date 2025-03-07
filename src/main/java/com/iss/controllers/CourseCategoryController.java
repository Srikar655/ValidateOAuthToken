package com.iss.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iss.Services.CourseCategoryService;
import com.iss.models.CourseCategory;

@RestController
@RequestMapping("/api/coursecategories")
public class CourseCategoryController {

    private final CourseCategoryService courseCategoryService;


    public CourseCategoryController(CourseCategoryService courseCategoryService) {
        this.courseCategoryService = courseCategoryService;
    }
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	    @GetMapping
	    public List<CourseCategory> getAllCourseCategories() {
	        return courseCategoryService.getAllCourseCategories();
	    }

	    // Fetch a course category by ID
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @GetMapping("/{id}")
	    public ResponseEntity<CourseCategory> getCourseCategoryById(@PathVariable int id) {
	        Optional<CourseCategory> courseCategory = courseCategoryService.getCourseCategoryById(id);
	        return courseCategory.map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    // Create a new course category
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @PostMapping
	    public CourseCategory createCourseCategory(@RequestBody CourseCategory courseCategory) {
	        return courseCategoryService.saveOrUpdateCourseCategory(courseCategory);
	    }

	    // Update an existing course category
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @PutMapping()
	    public ResponseEntity<CourseCategory> updateCourseCategory( @RequestBody CourseCategory updatedCategory) {
	        try {
	            CourseCategory courseCategory = courseCategoryService.updateCourseCategory( updatedCategory);
	            return ResponseEntity.ok(courseCategory);
	        } catch (RuntimeException e) {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    // Delete a course category by ID
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteCourseCategory(@PathVariable int id) {
	        courseCategoryService.deleteCourseCategoryById(id);
	        return ResponseEntity.noContent().build();
	    }
}
