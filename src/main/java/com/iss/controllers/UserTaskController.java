package com.iss.controllers;

import com.iss.Dto.UserTaskDto;
import com.iss.Services.UserTaskService;
import com.iss.models.UserTask;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-tasks")
public class UserTaskController {

    private final UserTaskService userTaskService;

    public UserTaskController(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }

    // Get all UserTasks (Paginated)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<UserTaskDto>> getAllUserTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<UserTaskDto> userTasks = userTaskService.findAll(pageable);

        if (userTasks != null && !userTasks.isEmpty()) {
            return new ResponseEntity<>(userTasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Get UserTasks by UserVedio ID (Paginated)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/uservedio/{userVedioId}")
    public ResponseEntity<List<UserTaskDto>> getUserTasksByUserVedioId(
            @PathVariable int userVedioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<UserTaskDto> userTasks = userTaskService.findByUserVedioId(userVedioId, pageable);

        if (userTasks != null && !userTasks.isEmpty()) {
            return new ResponseEntity<>(userTasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Get UserTask by ID
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserTaskDto> getUserTaskById(@PathVariable("id") int id) {
        UserTaskDto userTaskDto = userTaskService.find(id);
        if (userTaskDto != null) {
            return new ResponseEntity<>(userTaskDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add a new UserTask
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<UserTaskDto> addUserTask(@RequestBody UserTask userTask) {
        try {
            UserTaskDto addedUserTask = userTaskService.add(userTask);
            return new ResponseEntity<>(addedUserTask, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete UserTask by ID
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserTask(@PathVariable("id") int id) {
        try {
            userTaskService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
