package com.iss.controllers;

import com.iss.Dto.UserVedioDto;
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
@RequestMapping("/api/user-vedios")
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
    @GetMapping("/by-usercourse/{usercourseId}")
    public ResponseEntity<List<UserVedioDto>> getUserVediosByUserCourseId(
            @PathVariable("usercourseId") int usercourseId,
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<UserVedioDto> userVedios = userVedioService.findByUsercourseId(usercourseId, pageable);

        if (userVedios != null && !userVedios.isEmpty()) {
            return new ResponseEntity<>(userVedios, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
