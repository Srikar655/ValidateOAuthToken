package com.iss.controllers;

import com.iss.Dto.VideoCreateDto;
import com.iss.Dto.VideoDetailDto;
import com.iss.Dto.VideoListDisplayDto;
import com.iss.Dto.VideoUpdateDto;
import com.iss.Services.VedioService;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VedioService vedioService;

    public VideoController(VedioService vedioService) {
        this.vedioService = vedioService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addVideo(@RequestBody VideoCreateDto videoCreateDto) {
        try {
            VideoDetailDto addedVideo = vedioService.add(videoCreateDto);
            return ResponseEntity.ok(addedVideo);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding the video: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateVideo(@RequestBody VideoUpdateDto videoDto) {
    	if(videoDto.getId() < 0)
    	{
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    	}
    	System.out.println(videoDto.getVideoTitle());
        try {
        	VideoDetailDto updatedVideo = vedioService.update(videoDto);
                return ResponseEntity.ok(updatedVideo);
           
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
            Page<VideoListDisplayDto> videos = vedioService.findByCourseId(courseId, pageable);
            return ResponseEntity.ok(videos);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching videos: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getVideo(@PathVariable("id") int videoId) {
        try {
        	VideoDetailDto video = vedioService.find(videoId);
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
