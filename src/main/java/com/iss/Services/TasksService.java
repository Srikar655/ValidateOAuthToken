package com.iss.Services;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

import com.iss.Dto.TaskCreateDto;
import com.iss.Dto.TaskListDisplayDto;
import com.iss.Dto.TaskUpdateDto;
import com.iss.Dto.TasksDetailDto;
import com.iss.Dto.TasksDto;
import com.iss.Mappers.TasksMapper;
import com.iss.Repos.TasksRepository;
import com.iss.Repos.VedioRepository;
import com.iss.models.Tasks;
import com.iss.models.Vedio;

@Service
public class TasksService {

    private final TasksRepository repos;
    private final VedioRepository videoRepos;

    public TasksService(TasksRepository repos,VedioRepository videoRepos) {
        this.repos = repos;
        this.videoRepos = videoRepos;
    }

    @CacheEvict(value = "tasks", key = "#taskCreateDto.videoId") 
    @Transactional
    public TasksDetailDto add(TaskCreateDto taskCreateDto) {
        try {
        	Tasks task = this.buildTask(taskCreateDto);
        	Tasks savedTask = this.repos.save(task);
        	return this.buildTasksDetailDto(savedTask);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding Task", ex);
        }
    }
    private TasksDetailDto buildTasksDetailDto(Tasks task)
    {
    	return TasksDetailDto.builder()
    					.gitHubReferenceUrl(task.getGitHubReferenceUrl())
    					.id(task.getId())
    					.pdfResourceUrl(task.getPdfResourceUrl())
    					.points(task.getPoints())
    					.submissionType(task.getSubmissionType())
    					.task(task.getTask())
    					.taskLevel(task.getTaskLevel())
    					.taskPrice(task.getTaskprice())
    					.taskUrl(task.getTaskurl())
    					.taskDescription(task.getTaskDescription())
    					.build();
    }
    private Tasks buildTask(TaskCreateDto taskCreateDto) throws NotFoundException
    {
    	Vedio video = this.videoRepos.findById(taskCreateDto.getVideoId()).orElseThrow(()->new NotFoundException());
    	return Tasks.builder()
    					.gitHubReferenceUrl(taskCreateDto.getGitHubReferenceUrl())
    					.pdfResourceUrl(taskCreateDto.getPdfResourceUrl())
    					.points(taskCreateDto.getPoints())
    					.submissionType(taskCreateDto.getSubmissionType())
    					.task(taskCreateDto.getTask())
    					.taskLevel(taskCreateDto.getTaskLevel())
    					.taskprice(taskCreateDto.getTaskPrice())
    					.taskurl(taskCreateDto.getTaskUrl())
    					.taskDescription(taskCreateDto.getTaskDescription())
    					.video(video)
    					.build();
    }
    @CacheEvict(value = "tasks", allEntries = true)
    public List<TasksDto> add(List<Tasks> tasks) {
        try {
            return TasksMapper.Instance.toDtoList(repos.saveAllAndFlush(tasks));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding multiple Tasks", ex);
        }
    }

    public List<TasksDto> findAll() {
        try {
            return TasksMapper.Instance.toDtoList(repos.findAll());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching all Tasks", ex);
        }
    }
    
    @Cacheable(value = "tasks", key = "#videoId")
    @Transactional(readOnly = true)
    public List<TaskListDisplayDto> findAllByVideoId(int videoId)
    {
    	try {
    		return repos.findTasksByVideoId(videoId);
    	}
    	catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching all Tasks", ex);
        }
    }
    @Cacheable(value = "task", key = "#id")
    @Transactional(readOnly = true)
    public TasksDetailDto find(int id) {
        try {
            Tasks task = repos.findById(id).orElseThrow(()->new NotFoundException());
            return this.buildTasksDetailDto(task);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching Task with id " + id, ex);
        }
    }

    @Caching(
            put = { @CachePut(value = "task", key = "#taskUpdateDto.id") },
            evict = { @CacheEvict(value = "tasks", key = "#taskUpdateDto.videoId") } 
        )
        @Transactional
    public TasksDetailDto update(TaskUpdateDto taskUpdateDto) {
        try {
            Tasks existingTask = repos.findById(taskUpdateDto.getId()).orElseThrow();
            this.updateTaskDetails(existingTask, taskUpdateDto);
           Tasks updatedTask = repos.save(existingTask);
           return this.buildTasksDetailDto(updatedTask);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error updating Task with id " + taskUpdateDto.getId(), ex);
        }
    }
    private void updateTaskDetails(Tasks existingTask,TaskUpdateDto taskUpdateDto)
    {
    	existingTask.setGitHubReferenceUrl(taskUpdateDto.getGitHubReferenceUrl());
    	existingTask.setPdfResourceUrl(taskUpdateDto.getPdfResourceUrl());
    	existingTask.setTask(taskUpdateDto.getTask());
    	existingTask.setTaskDescription(taskUpdateDto.getTaskDescription());
    	existingTask.setPoints(taskUpdateDto.getPoints());
    	existingTask.setTaskLevel(taskUpdateDto.getTaskLevel());
    	existingTask.setSubmissionType(taskUpdateDto.getSubmissionType());
    	existingTask.setTaskprice(taskUpdateDto.getTaskPrice());
    	existingTask.setTaskurl(taskUpdateDto.getTaskUrl());
    }
    @Caching(
            evict = {
                @CacheEvict(value = "task", key = "#id"),
                @CacheEvict(value = "tasks", key = "#result.video.id") 
            }
        )
    @Transactional
    public void delete(int id) {
        try {
            if (repos.existsById(id)) {
                repos.deleteById(id);
            } else {
                throw new RuntimeException("Task with id " + id + " not found, delete failed.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error deleting Task with id " + id, ex);
        }
    }
}
