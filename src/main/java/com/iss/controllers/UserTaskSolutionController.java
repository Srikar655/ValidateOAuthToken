package com.iss.controllers;

import com.iss.Dto.UserTaskSolutionDto;
import com.iss.Dto.UserTaskSolutionImagesDto;
import com.iss.Services.CreateUsersTaskSolutionDto;
import com.iss.Services.NotificationService;
import com.iss.Services.UserTaskSolutionService;
import com.iss.models.UsersTaskSolution;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user-task-solutions")
public class UserTaskSolutionController {

    
    private final UserTaskSolutionService userTaskSolutionService;
    private final NotificationService notificationService;

    public UserTaskSolutionController(UserTaskSolutionService userTaskSolutionService,NotificationService notificationService) {
        this.userTaskSolutionService = userTaskSolutionService;
        this.notificationService=notificationService;
    }

   
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUserTaskSolutions(@RequestParam int page,@RequestParam int size) {
    	Pageable pageable = PageRequest.of(page, size);
        List<UserTaskSolutionDto> userTaskSolutions = userTaskSolutionService.findAllSubmittedSolutions(pageable);
        if (userTaskSolutions != null && !userTaskSolutions.isEmpty()) {
            return new ResponseEntity<>(userTaskSolutions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all-solution-images")
    public ResponseEntity<?> getSolutionImages(@RequestParam int userSolutionId) {
        UserTaskSolutionImagesDto userTaskSolutions = userTaskSolutionService.findSolutionImages(userSolutionId);
        if (userTaskSolutions != null && userTaskSolutions.getSolutionimages()!=null) {
            return new ResponseEntity<>(userTaskSolutions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserTaskSolutionById(@PathVariable("id") int id) {
        UserTaskSolutionDto userTaskSolutionDto = userTaskSolutionService.find(id);
        if (userTaskSolutionDto != null) {
            return new ResponseEntity<>(userTaskSolutionDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addUserTaskSolution(
            @RequestPart("solutionData") CreateUsersTaskSolutionDto userTaskSolution,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal Jwt jwt) {
    	System.out.println(userTaskSolution.getUserTaskId());
        try {
            String email = jwt.getClaimAsString("email");
            UserTaskSolutionDto addedUserTaskSolutionDto = this.userTaskSolutionService.add(userTaskSolution, files, email);
            return new ResponseEntity<>(addedUserTaskSolutionDto, HttpStatus.CREATED);
        }catch(DuplicateKeyException ex) {
        	return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    

    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("/review-success")
    public ResponseEntity<?> reviewSuccess(@RequestBody UsersTaskSolution userTaskSolution) {
        try {
        	System.out.println(userTaskSolution.getId()+""+userTaskSolution.getCorrectionStatus());
        	
           this.userTaskSolutionService.updateSuccessReview(userTaskSolution);
            return new ResponseEntity<>(userTaskSolution, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserTaskSolution(@PathVariable("id") int id) {
        try {
            this.userTaskSolutionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    
    
}
