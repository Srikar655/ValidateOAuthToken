package com.iss.controllers;

import com.iss.Dto.TaskImageDto;
import com.iss.Services.TaskImageService;
import com.iss.models.TaskImages;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taskImages")
public class TaskImageController {

    private final TaskImageService taskImageService;

    public TaskImageController(TaskImageService taskImageService) {
        this.taskImageService = taskImageService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<TaskImageDto>> getTaskImages(@RequestParam int taskId) {
        List<TaskImageDto> taskImages = taskImageService.findByTaskId(taskId);
        return ResponseEntity.ok(taskImages);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTaskImage(@RequestParam int imageId) {
        taskImageService.delete(imageId);
        return ResponseEntity.ok("Task Image Deleted Successfully");
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> saveTaskImage(@RequestBody TaskImages taskImageDto) {
        TaskImageDto savedTaskImage = taskImageService.save(taskImageDto);
        return ResponseEntity.ok(savedTaskImage);
    }
}
