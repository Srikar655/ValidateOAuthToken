package com.iss.controllers;

import com.iss.Dto.VideoDto;
import com.iss.Services.VedioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VedioService vedioService;

    public VideoController(VedioService vedioService) {
        this.vedioService = vedioService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addVideo(@RequestBody VideoDto videoDto) {
        try {
            VideoDto addedVideo = vedioService.add(videoDto);
            return ResponseEntity.ok(addedVideo);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding the video: " + ex.getMessage());
        }
    }

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
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating the video: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/getAll")
    public ResponseEntity<?> getVideoByCourseId(@RequestParam int courseId, @RequestParam int size, @RequestParam int page) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<VideoDto> videos = vedioService.findByCourseId(courseId, pageable);
            return ResponseEntity.ok(videos);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching videos: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/get")
    public ResponseEntity<?> getVideo(@RequestParam int videoId) {
        try {
            VideoDto video = vedioService.find(videoId);
            return ResponseEntity.ok(video);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching video: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVideo(@RequestParam int videoId) {
        try {
            vedioService.delete(videoId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting video: " + ex.getMessage());
        }
    }
}
