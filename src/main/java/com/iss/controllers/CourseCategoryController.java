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


        public CourseCategoryController(CourseCategoryService courseCategoryService) 
        {
        	this.courseCategoryService = courseCategoryService;
    	}
		@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	    @GetMapping
	    public List<CourseCategory> getAllCourseCategories() {
	        return courseCategoryService.getAllCourseCategories();
	    }
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @GetMapping("/{id}")
	    public ResponseEntity<?> getCourseCategoryById(@PathVariable int id) {
	    	try {
	    		CourseCategory courseCategory = courseCategoryService.getCourseCategoryById(id);
	    		return ResponseEntity.ok(courseCategory);
	    	}catch(Exception ex) {
	    		ex.printStackTrace();
	    		return ResponseEntity.status(500).body("Error adding the course: " + ex.getMessage());
	    	}
	        
	    }
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @PostMapping
	    public ResponseEntity<?> createCourseCategory(@RequestBody CourseCategory courseCategory) {
	    	try {
	    		System.out.println(courseCategory);
	    		return ResponseEntity.ok(courseCategoryService.saveOrUpdateCourseCategory(courseCategory));
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	    		return ResponseEntity.status(500).body("Error adding the course: " + ex.getMessage());
	        }
	        
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
	    public ResponseEntity<?> deleteCourseCategory(@PathVariable int id) {
	    	
	    	try {
	    		courseCategoryService.deleteCourseCategoryById(id);
		        return ResponseEntity.noContent().build();
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	    		return ResponseEntity.status(500).body("Error adding the course: " + ex.getMessage());
	        }
	        
	    }
}
