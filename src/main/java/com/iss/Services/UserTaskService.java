package com.iss.Services;

import com.iss.Dto.UserTaskDetailDisplayDto;
import com.iss.Dto.UserTaskDto;
import com.iss.Dto.UserTaskListDisplayDto;
import com.iss.Mappers.UserTaskMapper;
import com.iss.Repos.UserTaskRepsitory;
import com.iss.models.AccessStatus;
import com.iss.models.PaymentStatus;
import com.iss.models.Tasks;
import com.iss.models.UserTask;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserTaskService {

    private final UserTaskRepsitory repos;

    public UserTaskService(UserTaskRepsitory repos) {
        this.repos = repos;
    }
    @CacheEvict(value = "userTasksByUserVideo", key = "#userTask.userVideo.id") // Assumes UserTask has a 'userVideo' field
    @Transactional
    public UserTaskDto add(UserTask userTask) {
        try {
            UserTask savedTask = repos.save(userTask);
            return UserTaskMapper.Instance.toDto(savedTask);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding the UserTask", ex);
        }
    }
    
    public List<UserTaskDto> findAll(Pageable pageable) {
        try {
            Page<UserTask> page = repos.findAll(pageable);
            return UserTaskMapper.Instance.toDtoList(page.getContent());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching paginated UserTasks", ex);
        }
    }
    
	@Cacheable(value = "userTasksByUserVideo", key = "#userVideoId")
	@Transactional(readOnly = true)
   public List<UserTaskListDisplayDto> findByUserVedioId(int userVideoId) {
	    try {
	        List<UserTaskListDisplayDto> list = repos.findUserTasksByUserVedioId(userVideoId);
	        return list;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        throw new RuntimeException("Error fetching UserTasks by UserVedio ID", ex);
	    }
	}

   @Cacheable(value = "userTask", key = "#id")
   @Transactional(readOnly = true)
    public UserTaskDetailDisplayDto find(int id) {
        try {
            Optional<UserTask> userTaskOpt = repos.findById(id);
            if(userTaskOpt.isPresent())
            {
            	return buildUserTaskDetailDisplayDto(userTaskOpt.get());
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching the UserTask with id " + id, ex);
        }
    }
    private UserTaskDetailDisplayDto buildUserTaskDetailDisplayDto(UserTask userTask)
    {
    	Tasks task = userTask.getTask();
       
        UserTaskDetailDisplayDto dto = new UserTaskDetailDisplayDto(
            userTask.getId(),        
            task.getId(),            
            task.getTask(),          
            task.getTaskprice(),     
            task.getTaskLevel(),     
            task.getPoints(),        
            userTask.getPaymentStatus(),
            userTask.getAccessStatus(), 
            userTask.getTaskProgress() 
        );
        if (userTask.getAccessStatus() == AccessStatus.UNLOCKED && userTask.getPaymentStatus() == PaymentStatus.COMPLETED) {
            
            dto.setTaskUrl(task.getTaskurl()); 
            dto.setSubmissionType(task.getSubmissionType());
            if (task.getTaskimages() != null) {
                dto.setGitHubReferenceUrl(task.getGitHubReferenceUrl());
                dto.setPdfResourceUrl(task.getPdfResourceUrl());
            }
        }
        return dto;
    }
    @Transactional(readOnly = true)
    public UserTask findAndGetUserTask(int id) {
        try {
            Optional<UserTask> userTaskOpt = repos.findById(id);
            if(userTaskOpt.isPresent())
            {
            	return userTaskOpt.get();
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching the UserTask with id " + id, ex);
        }
    }
    @Caching(
            put = { @CachePut(value = "userTask", key = "#userTask.id") },
            evict = { @CacheEvict(value = "userTasksByUserVideo", key = "#userTask.userVideo.id") }
        )
        @Transactional
    public UserTaskDto update(UserTask task)
    {
    	try
    	{
    		Optional<UserTask> optionalTask=repos.findById(task.getId());
    		if(optionalTask.isPresent())
    		{
    			return UserTaskMapper.Instance.toDto(repos.save(task));
    		}
    		return null;
    	}catch(Exception ex)
    	{
    		ex.printStackTrace();
    		return null;
    	}
    }
    @Caching(
            evict = {
                @CacheEvict(value = "userTask", key = "#id"),
                @CacheEvict(value = "userTasksByUserVideo", key = "#result.userVideo.id")
            }
        )
        @Transactional
    public void delete(int id) {
        try {
            if (repos.existsById(id)) {
                repos.deleteById(id);
            } else {
                throw new RuntimeException("UserTask with id " + id + " not found, delete failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error deleting the UserTask with id " + id, ex);
        }
    }
}
