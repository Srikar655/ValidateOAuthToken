package com.iss.controllers;

import com.iss.Dto.TaskCreateDto;
import com.iss.Dto.TaskListDisplayDto;
import com.iss.Dto.TaskUpdateDto;
import com.iss.Dto.TasksDetailDto;
import com.iss.Services.TasksService;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody TaskCreateDto taskCreateDto) {
        try {
        	TasksDetailDto addedTask = tasksService.add(taskCreateDto);
            if (addedTask != null) {
                return ResponseEntity.ok(addedTask);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Unable to add task.");
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
        	return ResponseEntity.status(500).body("Error adding the task: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody TaskUpdateDto taskUpdateDto) {
        try {
        	TasksDetailDto updatedTask = tasksService.update(taskUpdateDto);
            if (updatedTask != null) {
                return ResponseEntity.ok(updatedTask);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Task not found.");
            }
        } catch (Exception ex) {
        	return ResponseEntity.status(500).body("Error updating the task: " + ex.getMessage());
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getTask(@PathVariable int id) {
        try {
        	TasksDetailDto task = tasksService.find(id);
            if (task != null) {
                return ResponseEntity.ok(task);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Task not found.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/getAllByVideoId/{videoId}")
    public ResponseEntity<?> getAllTasksByVideoId(@PathVariable("videoId") int videoId) {
        try {
        	List<TaskListDisplayDto> taskListDisplayDto = tasksService.findAllByVideoId(videoId);
            if (!taskListDisplayDto.isEmpty()) {
                return ResponseEntity.ok(taskListDisplayDto);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }

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
