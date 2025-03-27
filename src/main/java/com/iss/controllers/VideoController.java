package com.iss.controllers;

import com.iss.Dto.VideoDto;
import com.iss.Services.VedioService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VedioService vedioService;

    public VideoController(VedioService vedioService) {
        this.vedioService = vedioService;
    }

    // Adding video
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addVideo(@RequestBody VideoDto videoDto) {
        try {
            VideoDto addedVideo = vedioService.add(videoDto);
            if (addedVideo != null) {
                return ResponseEntity.ok(addedVideo);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Unable to add video.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }

    // Updating video
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateVideo(@RequestBody VideoDto videoDto) {
        try {
            VideoDto updatedVideo = vedioService.update(videoDto);
            if (updatedVideo != null) {
                return ResponseEntity.ok(updatedVideo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Video not found.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }

    // Getting videos related to a course
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<?> getVideoByCourseId(@RequestParam int courseId, @RequestParam int size, @RequestParam int page) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<VideoDto> videos = vedioService.findByCourseId(courseId, pageable);
            if (!videos.isEmpty()) {
                return ResponseEntity.ok(videos);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No videos found for this course.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }

    // Getting a specific video
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get")
    public ResponseEntity<?> getVideo(@RequestParam int videoId) {
        try {
            VideoDto video = vedioService.find(videoId);
            if (video != null) {
                return ResponseEntity.ok(video);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Video not found.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }

    // Deleting a video
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVideo(@RequestParam int videoId) {
        try {
            vedioService.delete(videoId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Video deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }
}
