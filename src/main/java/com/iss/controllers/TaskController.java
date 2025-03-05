package com.iss.controllers;


import com.iss.Dto.TasksDto;
import com.iss.Services.TasksService;
import com.iss.models.Tasks;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TasksService tasksService;

    public TaskController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody Tasks tasks) {
        return ResponseEntity.ok(tasksService.add(tasks));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody Tasks tasks) {
        return ResponseEntity.ok(tasksService.update(tasks));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<TasksDto>> getTasks(@RequestParam int videoId, @RequestParam int size, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<TasksDto> tasks = tasksService.findByVideoId(videoId, pageable);
        return ResponseEntity.ok(tasks);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get")
    public ResponseEntity<?> getTask(@RequestParam int taskId) {
        return ResponseEntity.ok(tasksService.find(taskId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTask(@RequestParam int taskId) {
        tasksService.delete(taskId);
        return ResponseEntity.ok("Task Deleted Successfully");
    }
}

