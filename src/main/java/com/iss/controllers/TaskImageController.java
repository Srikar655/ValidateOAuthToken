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

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/getAll")
    public ResponseEntity<?> getTaskImages(@RequestParam int taskId) {
    	try
    	{
    		List<TaskImageDto> taskImages = taskImageService.findByTaskId(taskId);
    		return ResponseEntity.ok(taskImages);
    	}catch(Exception ex) {
    		return ResponseEntity.status(500).body("Error getting the task Images: " + ex.getMessage());
    	}
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTaskImage(@RequestParam int imageId) {
    	
    	try
    	{
    		taskImageService.delete(imageId);
            return ResponseEntity.ok("Task Image Deleted Successfully");
    	}catch(Exception ex) {
    		return ResponseEntity.status(500).body("Error deleting the task Images: " + ex.getMessage());
    	}
        
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> saveTaskImage(@RequestBody TaskImages taskImageDto) {
    	try
    	{
            TaskImageDto savedTaskImage = taskImageService.save(taskImageDto);
            return ResponseEntity.ok(savedTaskImage);
    	}catch(Exception ex) {
    		return ResponseEntity.status(500).body("Error saving the task Images: " + ex.getMessage());
    	}
    }
}
