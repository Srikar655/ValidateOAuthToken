package com.iss.controllers;

import com.iss.Dto.UserTaskSolutionDto;
import com.iss.Dto.UserTaskSolutionImagesDto;
import com.iss.Services.NotificationService;
import com.iss.Services.UserTaskSolutionService;
import com.iss.models.NotificationType;
import com.iss.models.Notifications;
import com.iss.models.UsersTaskSolution;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user-task-solution")
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
    @PostMapping
    public ResponseEntity<?> addUserTaskSolution(@RequestBody UsersTaskSolution userTaskSolution,@AuthenticationPrincipal Jwt jwt) {
        try {
        	String emailString=jwt.getClaimAsString("email");
        	userTaskSolution.setSubmittedAt(new Timestamp(System.currentTimeMillis()));
        	userTaskSolution.setEmail(emailString);
        	String message = "📝 New Task Submitted by " + emailString;
            UserTaskSolutionDto addedUserTaskSolutionDto = this.userTaskSolutionService.add(userTaskSolution);
            Notifications notification=Notifications.builder().sender(emailString).receiver("ADMIN").message(message).read(false).type(NotificationType.INFO).build();
            this.notificationService.sendNotification(notification);
            return new ResponseEntity<>(addedUserTaskSolutionDto, HttpStatus.CREATED);
        } catch (Exception ex) {
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
