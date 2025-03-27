package com.iss.controllers;

import com.iss.Dto.TasksDto;
import com.iss.Services.TasksService;
import com.iss.models.Tasks;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    // Task add cheyyadam
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody Tasks tasks) {
        try {
            TasksDto addedTask = tasksService.add(tasks);
            if (addedTask != null) {
                return ResponseEntity.ok(addedTask);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Unable to add task.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }

    // Task update cheyyadam
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody Tasks tasks) {
        try {
            TasksDto updatedTask = tasksService.update(tasks);
            if (updatedTask != null) {
                return ResponseEntity.ok(updatedTask);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Task not found.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }

    // Video ki sambandhichina tasks ni get cheyyadam
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<?> getTasksByVideoId(@RequestParam int videoId, @RequestParam int size, @RequestParam int page) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<TasksDto> tasks = tasksService.findByVideoId(videoId, pageable);
            if (!tasks.isEmpty()) {
                return ResponseEntity.ok(tasks);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No tasks available for this video.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }

    // Specific task ni get cheyyadam
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get")
    public ResponseEntity<?> getTask(@RequestParam int taskId) {
        try {
            TasksDto task = tasksService.find(taskId);
            if (task != null) {
                return ResponseEntity.ok(task);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Task not found.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }

    // Task delete cheyyadam
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTask(@RequestParam int taskId) {
        try {
            tasksService.delete(taskId);
            return ResponseEntity.ok("Task deleted successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }
}
