package com.iss.controllers;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iss.Services.CourseService;
import com.iss.Services.CustomUserDetailsService;
import com.iss.Services.TaskImageService;
import com.iss.Services.TasksService;
import com.iss.Services.VedioService;
import com.iss.models.Course;
import com.iss.Dto.*;
import com.iss.models.Tasks;

@RestController
@RequestMapping("/api")

public class ValidController
{
	private final CourseService courseService;
	private final TasksService	tasksService;
	private final VedioService	vedioService;
	private final TaskImageService taskImageService;
	private final CustomUserDetailsService userService;
	public ValidController(CourseService courseService,TasksService tasksService,VedioService vedioService,TaskImageService taskImageService,CustomUserDetailsService userService)
	{
		this.courseService=courseService;
		this.tasksService=tasksService;
		this.vedioService=vedioService;
		this.taskImageService=taskImageService;
		this.userService=userService;
	}
	@GetMapping("/userinfo")
    public ResponseEntity<Map<String, Object>> getUserInfo(@AuthenticationPrincipal Jwt jwt) {

        String email = jwt.getClaimAsString("email");
        String name = jwt.getClaimAsString("name");
        List<String> audience = jwt.getAudience();

        Map<String, Object> userInfo = Map.of(
            "email", email,
            "name", name,
            "audience",audience
        );

        return ResponseEntity.ok(userInfo);
    }
	@GetMapping("/login")
    public ResponseEntity<?> login(@AuthenticationPrincipal Jwt jwt) {
		
        return ResponseEntity.ok(userService.loadUserByUsername(jwt.getClaimAsString("email")));
    }	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/addCourse")
	public ResponseEntity<?> addCourseData(@RequestBody Course course)throws Exception
	{	
		CourseDto c=courseService.add(course);
		return ResponseEntity.ok(c);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/editCourse")
	public ResponseEntity<?> updateCourse(@RequestBody Course course)throws Exception
	{	
		CourseDto c=courseService.update(course);
		return ResponseEntity.ok(c);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/getCourse")
	public ResponseEntity<?> getCourses()  
	{

	    List<CourseDto> list = courseService.findAll();
	    return ResponseEntity.ok(list);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/deleteCourse")
	public ResponseEntity<?> deleteCourse(@RequestParam int courseId){
		courseService.delete(courseId);
		return ResponseEntity.ok(courseId);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/findCourse")
	public ResponseEntity<?> getCourses(@RequestParam int courseId) {
	    CourseDto course = courseService.find(courseId);
	    return ResponseEntity.ok(course);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value="/findCourseThumbnail")
	public ResponseEntity<?> getCoursesthumbnail(@RequestBody int courseId) {
        return  ResponseEntity.ok(courseService.findThumbnail(courseId));
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/getVideos")
	public ResponseEntity<?> getVideos(@RequestParam int courseId,@RequestParam int size,@RequestParam int page)
	{
		Pageable pageable=PageRequest.of(page, size);
		List<VideoDto>	list=vedioService.findByCourseId(courseId,pageable);
		return ResponseEntity.ok(list);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/findVideo")
	public ResponseEntity<?> findVideo(@RequestParam int videoId)
	{
		return ResponseEntity.ok(vedioService.find(videoId));
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/deleteVideo")
	public ResponseEntity<?> deleteVideo(@RequestParam int videoId)
	{
		vedioService.delete(videoId);
		Map<String,String> map=new HashMap<String,String>();
		map.put("response","Deletion Successfull");
			return ResponseEntity.ok(map);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/addVideo")
	public ResponseEntity<?> addVedioData(@RequestBody VideoDto vedio)
	{
		return ResponseEntity.ok(vedioService.add(vedio));
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/updateVideo")
	public ResponseEntity<?> updateVideo(@RequestBody VideoDto vedio)
	{
		return ResponseEntity.ok(vedioService.update(vedio));
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/addTask")
	public ResponseEntity<?> addTask(@RequestBody Tasks tasks)
	{
		return ResponseEntity.ok(tasksService.add(tasks));
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/deleteTask")
	public ResponseEntity<?> deleteTask(@RequestParam int taskId)
	{
		tasksService.delete(taskId);
		Map<String,String> map=new HashMap<String,String>();
		map.put("response","Deletion Successfull");
			return ResponseEntity.ok(map);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/findTask")
	public ResponseEntity<?> findTask(@RequestParam int taskId)
	{
			return ResponseEntity.ok(tasksService.find(taskId));
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/updateTask")
	public ResponseEntity<?> updateTask(@RequestBody Tasks tasks)
	{
		return ResponseEntity.ok(tasksService.update(tasks));
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/getTasks")
	public ResponseEntity<?> getTasks(@RequestParam int videoId,@RequestParam int size,@RequestParam int page)
	{
		Pageable pageable=PageRequest.of(page, size);
		List<TasksDto>	list=tasksService.findByVideoId(videoId,pageable);
		return ResponseEntity.ok(list);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/findTaskImages")
	public ResponseEntity<?> getTasks(@RequestParam int taskId)
	{
		List<TaskImageDto>	list=taskImageService.findByTaskId(taskId);
		System.out.println("got");
		return ResponseEntity.ok(list);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/deleteTaskImage")
	public ResponseEntity<?> deleteTaskImage(@RequestParam int imageId)
	{
		taskImageService.delete(imageId);
		Map<String,String> map=new HashMap<String,String>();
		map.put("response","Deletion Successfull");
		return ResponseEntity.ok(map);
	}
}