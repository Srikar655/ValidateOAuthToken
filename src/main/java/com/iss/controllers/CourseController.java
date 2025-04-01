package com.iss.controllers;



import com.iss.Dto.CourseDto;
import com.iss.Services.CourseService;
import com.iss.models.Course;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        try {
            CourseDto c = courseService.add(course);
            return ResponseEntity.ok(c);
        } catch (Exception ex) {
        	ex.printStackTrace();
            return ResponseEntity.status(500).body("Error adding the course: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateCourse(@RequestBody Course course) {
        try {
            CourseDto c = courseService.update(course);
            if(c!=null) {
            	return ResponseEntity.ok(c);
            }
            else
            {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Video not found.");
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
            return ResponseEntity.status(500).body("Error updating the course: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCourses() {
        try {
            List<CourseDto> courses = courseService.findAll();
            return ResponseEntity.ok(courses);
        } catch (Exception ex) {
        	ex.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching courses: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/get")
    public ResponseEntity<?> getCourse(@RequestParam int courseId) {
        try {
            CourseDto course = courseService.find(courseId);
            return ResponseEntity.ok(course);
        } catch (Exception ex) {
        	ex.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching course with ID: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCourse(@RequestParam int courseId) {
        try {
            courseService.delete(courseId);
            return ResponseEntity.ok("Course deleted successfully");
        } catch (Exception ex) {
        	ex.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting course: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/findCourseThumbnail")
    public ResponseEntity<?> getCourseThumbnail(@RequestParam int courseId) {
        try {
        	System.out.println("Finding course thumnail");
            return ResponseEntity.ok(courseService.findThumbnail(courseId));
        } catch (Exception ex) {
        	ex.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching course thumbnail: " + ex.getMessage());
        }
    }
}
