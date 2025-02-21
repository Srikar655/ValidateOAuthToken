package com.iss.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iss.Services.CourseService;
import com.iss.Services.TaskImageService;
import com.iss.Services.TasksService;
import com.iss.Services.VedioService;
import com.iss.models.Course;
import com.iss.Dto.*;
import com.iss.models.Tasks;
import com.iss.models.Vedio;

@RestController
@RequestMapping("/api")

public class ValidController
{
	private final CourseService courseService;
	private final TasksService	tasksService;
	private final VedioService	vedioService;
	private final TaskImageService taskImageService;
	public ValidController(CourseService courseService,TasksService tasksService,VedioService vedioService,TaskImageService taskImageService)
	{
		this.courseService=courseService;
		this.tasksService=tasksService;
		this.vedioService=vedioService;
		this.taskImageService=taskImageService;
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
	
	@PostMapping("/addCourse")
	public ResponseEntity<?> addCourseData(@RequestBody Course course)throws Exception
	{	
		CourseDto c=courseService.add(course);
		return ResponseEntity.ok(c);
	}

	@PostMapping("/editCourse")
	public ResponseEntity<?> updateCourse(@RequestBody Course course)throws Exception
	{	
		CourseDto c=courseService.update(course);
		return ResponseEntity.ok(c);
	}
	@GetMapping("/getCourse")
	public ResponseEntity<?> getCourses()
	{
		List<CourseDto>	list=courseService.findAll();
		return ResponseEntity.ok(list);
	}
	@GetMapping("/deleteCourse")
	public ResponseEntity<?> deleteCourse(@RequestParam int courseId){
		courseService.delete(courseId);
		return ResponseEntity.ok(courseId);
	}
	
	@GetMapping("/findCourse")
	public ResponseEntity<?> getCourses(@RequestParam int courseId) {
	    CourseDto course = courseService.find(courseId);
	    return ResponseEntity.ok(course);
	}
	@PostMapping(value="/findCourseThumbnail")
	public ResponseEntity<?> getCoursesthumbnail(@RequestBody int courseId) {
        return  ResponseEntity.ok(courseService.findThumbnail(courseId));
	}

	@PostMapping("/getVideos")
	public ResponseEntity<?> getVideos(@RequestParam int courseId,@RequestParam int size,@RequestParam int page)
	{
		Pageable pageable=PageRequest.of(page, size);
		List<VideoDto>	list=vedioService.findByCourseId(courseId,pageable);
		return ResponseEntity.ok(list);
	}
	@GetMapping("/findVideo")
	public ResponseEntity<?> findVideo(@RequestParam int videoId)
	{
		return ResponseEntity.ok(vedioService.find(videoId));
	}
	@GetMapping("/deleteVideo")
	public ResponseEntity<?> deleteVideo(@RequestParam int videoId)
	{
		vedioService.delete(videoId);
		Map<String,String> map=new HashMap<String,String>();
		map.put("response","Deletion Successfull");
			return ResponseEntity.ok(map);
	}
	@PostMapping("/addVideo")
	public ResponseEntity<?> addVedioData(@RequestBody VideoDto vedio)
	{
		return ResponseEntity.ok(vedioService.add(vedio));
	}
	@PostMapping("/updateVideo")
	public ResponseEntity<?> updateVideo(@RequestBody VideoDto vedio)
	{
		return ResponseEntity.ok(vedioService.update(vedio));
	}
	@PostMapping("/addTask")
	public ResponseEntity<?> addTask(@RequestBody Tasks tasks)
	{
		return ResponseEntity.ok(tasksService.add(tasks));
	}
	@GetMapping("/deleteTask")
	public ResponseEntity<?> deleteTask(@RequestParam int taskId)
	{
		tasksService.delete(taskId);
		Map<String,String> map=new HashMap<String,String>();
		map.put("response","Deletion Successfull");
			return ResponseEntity.ok(map);
	}
	@GetMapping("/findTask")
	public ResponseEntity<?> findTask(@RequestParam int taskId)
	{
			return ResponseEntity.ok(tasksService.find(taskId));
	}
	@PostMapping("/updateTask")
	public ResponseEntity<?> updateTask(@RequestBody Tasks tasks)
	{
		return ResponseEntity.ok(tasksService.update(tasks));
	}
	@PostMapping("/getTasks")
	public ResponseEntity<?> getTasks(@RequestParam int videoId,@RequestParam int size,@RequestParam int page)
	{
		Pageable pageable=PageRequest.of(page, size);
		List<TasksDto>	list=tasksService.findByVideoId(videoId,pageable);
		return ResponseEntity.ok(list);
	}
	@GetMapping("/findTaskImages")
	public ResponseEntity<?> getTasks(@RequestParam int taskId)
	{
		List<TaskImageDto>	list=taskImageService.findByTaskId(taskId);
		System.out.println("got");
		return ResponseEntity.ok(list);
	}
	@GetMapping("/deleteTaskImage")
	public ResponseEntity<?> deleteTaskImage(@RequestParam int imageId)
	{
		taskImageService.delete(imageId);
		Map<String,String> map=new HashMap<String,String>();
		map.put("response","Deletion Successfull");
		return ResponseEntity.ok(map);
	}
}