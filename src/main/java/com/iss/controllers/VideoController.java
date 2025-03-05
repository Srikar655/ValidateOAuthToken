package com.iss.controllers;



import com.iss.Dto.VideoDto;
import com.iss.Services.VedioService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addVideo(@RequestBody VideoDto videoDto) {
        return ResponseEntity.ok(vedioService.add(videoDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateVideo(@RequestBody VideoDto videoDto) {
        return ResponseEntity.ok(vedioService.update(videoDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<VideoDto>> getVideos(@RequestParam int courseId, @RequestParam int size, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<VideoDto> videos = vedioService.findByCourseId(courseId, pageable);
        return ResponseEntity.ok(videos);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get")
    public ResponseEntity<?> getVideo(@RequestParam int videoId) {
        return ResponseEntity.ok(vedioService.find(videoId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVideo(@RequestParam int videoId) {
        vedioService.delete(videoId);
        Map<String, String> response = new HashMap<String,String>();
        response.put("message", "Video Deletion Successful");
        return ResponseEntity.ok(response);

    }
}
