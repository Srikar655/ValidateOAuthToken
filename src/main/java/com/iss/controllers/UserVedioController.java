package com.iss.controllers;

import com.iss.Dto.UserVedioDto;
import com.iss.Dto.UserVideoDetailDisplayDto;
import com.iss.Services.UserVedioService;
import com.iss.models.UserVedio;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-videos")
public class UserVedioController {

    private final UserVedioService userVedioService;

    public UserVedioController(UserVedioService userVedioService) {
        this.userVedioService = userVedioService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping
     public ResponseEntity<List<UserVedioDto>> getAllUserVedios(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<UserVedioDto> userVedios = userVedioService.findAll(pageable);

        if (userVedios != null && !userVedios.isEmpty()) {
            return new ResponseEntity<>(userVedios, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/all")
    public ResponseEntity<List<UserVedioDto>> getAllUserVedios() {
        List<UserVedioDto> userVedios = userVedioService.findAll();
        if (userVedios != null && !userVedios.isEmpty()) {
            return new ResponseEntity<>(userVedios, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserVedioDto> getUserVedioById(@PathVariable("id") int id) {
        UserVedioDto userVedioDto = userVedioService.find(id);
        if (userVedioDto != null) {
            return new ResponseEntity<>(userVedioDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/select")
    public ResponseEntity<UserVideoDetailDisplayDto> getUserVediosByUserCourseId(
    		@RequestParam int userCourseId,
            @RequestParam int videoId) {

    	try {
    		UserVideoDetailDisplayDto userVideoDetailDisplayDto = userVedioService.checkAccessAndGetVideoDetails(userCourseId,videoId);
            if (userVideoDetailDisplayDto != null) {
                return ResponseEntity.status(HttpStatus.OK).body(userVideoDetailDisplayDto);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<UserVedioDto> addUserVedio(@RequestBody UserVedio userVedioDto) {
        try {
            UserVedioDto addedUserVedio = userVedioService.add(userVedioDto);
            return new ResponseEntity<>(addedUserVedio, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserVedio(@PathVariable("id") int id) {
        try {
            userVedioService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
