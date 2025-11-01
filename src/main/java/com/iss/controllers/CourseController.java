package com.iss.controllers;



import com.iss.Dto.CourseCreateDto;
import com.iss.Dto.CourseDetailDto;
import com.iss.Dto.CourseFeaturesUpdateDto;
import com.iss.Dto.CourseListDisplayDto;
import com.iss.Dto.CourseUpdateDto;
import com.iss.Services.CourseService;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value= "/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addCourse(@RequestPart CourseCreateDto courseCreateDto,@RequestPart("courseThumbnail") MultipartFile courseThumbnail) {
        try {
        	CourseDetailDto c = courseService.add(courseCreateDto,courseThumbnail);
            return ResponseEntity.ok(c);
        } catch (Exception ex) {
        	ex.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCourse(@RequestPart("courseData") CourseUpdateDto courseUpdateDto,@RequestPart(value="courseThumbnail",required=false) MultipartFile courseThumbnail) {
    	if (courseUpdateDto.getId() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            CourseDetailDto c = courseService.update(courseUpdateDto,courseThumbnail);
            if(c!=null) {
            	return ResponseEntity.ok(c);
            }
            else
            {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Video not found.");
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/updateCourseFeatures")
    public ResponseEntity<?> updateCourseFeatures(@RequestBody CourseFeaturesUpdateDto courseFeaturesUpdateDto) {
    	if (courseFeaturesUpdateDto.getId() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            CourseDetailDto c = courseService.updateCourseFeatures(courseFeaturesUpdateDto);
            if(c!=null) {
            	return ResponseEntity.ok(c);
            }
            else
            {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Video not found.");
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCourses(
            @RequestParam(value = "categoryIds", required = false) List<Integer> categoryIds,
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            Pageable pageable) {
        try {
            Page<CourseListDisplayDto> courses = courseService.findAll(categoryIds, searchTerm, pageable);
            return ResponseEntity.ok(courses);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCourse(@PathVariable("id") int courseId) {
        try {
        	CourseDetailDto course = courseService.find(courseId);
            return ResponseEntity.ok(course);
        } catch (Exception ex) {
        	ex.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        try {
            courseService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
        	ex.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    
}
