package com.iss.controllers;



import com.iss.Dto.CourseDto;
import com.iss.Services.CourseService;
import com.iss.models.Course;

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
    public ResponseEntity<?> addCourse(@RequestBody Course course) throws Exception {
    	CourseDto c = courseService.add(course);
    	
        return ResponseEntity.ok(c);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateCourse(@RequestBody Course course) throws Exception {
        CourseDto c = courseService.update(course);
        return ResponseEntity.ok(c);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/getAll")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/get")
    public ResponseEntity<?> getCourse(@RequestParam int courseId) {
        CourseDto course = courseService.find(courseId);
        return ResponseEntity.ok(course);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCourse(@RequestParam int courseId) {
        courseService.delete(courseId);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/findCourseThumbnail")
	public ResponseEntity<?> getCoursesthumbnail(@RequestParam int courseId) {
        return  ResponseEntity.ok(courseService.findThumbnail(courseId));
	}
}

