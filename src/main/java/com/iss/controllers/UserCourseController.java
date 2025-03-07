package com.iss.controllers;

import com.iss.models.UserCourse;
import com.iss.Dto.UserCourseDto;
import com.iss.Services.UserCourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usercourses")
public class UserCourseController {

    private final UserCourseService userCourseService;

    public UserCourseController(UserCourseService userCourseService) {
        this.userCourseService = userCourseService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<UserCourseDto>> getAllUserCourses() {
        List<UserCourseDto> userCourses = userCourseService.findAll();
        if (userCourses != null && !userCourses.isEmpty()) {
            return new ResponseEntity<>(userCourses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserCourseDto> getUserCourseById(@PathVariable("id") int id) {
        UserCourseDto userCourseDto = userCourseService.find(id);
        if (userCourseDto != null) {
            return new ResponseEntity<>(userCourseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<UserCourseDto> addUserCourse(@RequestBody UserCourse userCourseDto) {
        try {
            UserCourseDto addedUserCourse = userCourseService.add(userCourseDto);
            return new ResponseEntity<>(addedUserCourse, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserCourse(@PathVariable("id") int id) {
        try {
            userCourseService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
