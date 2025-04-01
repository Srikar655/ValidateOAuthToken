package com.iss.Services;

import com.iss.Dto.TasksDto;
import com.iss.Dto.UserTaskDto;
import com.iss.Mappers.TaskImageMapper;
import com.iss.Mappers.UserTaskMapper;
import com.iss.Repos.UserTaskRepsitory;
import com.iss.models.AccessStatus;
import com.iss.models.PaymentStatus;
import com.iss.models.Tasks;
import com.iss.models.UserTask;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserTaskService {

    private final UserTaskRepsitory repos;

    public UserTaskService(UserTaskRepsitory repos) {
        this.repos = repos;
    }

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

    public List<UserTaskDto> findByUserVedioId(int userVedioId, Pageable pageable) {
        try {
            Optional<Page<UserTask>> optionalpage = repos.findByUservedio_Id(userVedioId, pageable);
            if(optionalpage.isPresent())
            {
            	List<UserTask> usertasks= optionalpage.get().getContent();
            	return usertasks.stream().map(ut->{
            		if(ut.getAccessStatus().equals(AccessStatus.UNLOCKED))
            		{
	            		UserTaskDto userTaskDto=UserTaskMapper.Instance.toDto(ut);
	            		if(ut.getPaymentStatus().equals(PaymentStatus.COMPLETED))
	            		{
	            			Tasks task=ut.getTask();
	            			TasksDto temptask=userTaskDto.getTask();
	            			temptask.setTaskimages(TaskImageMapper.Instance.toDtoList(task.getTaskimages()));
	            			temptask.setTask(task.getTask());
	            			temptask.setTaskurl(task.getTaskurl());
	            		}
	            		return userTaskDto;
            		}
            		UserTaskDto userTaskDto=new UserTaskDto();
            		BeanUtils.copyProperties(ut,userTaskDto,"task","payments","uservedio");
            		return userTaskDto;
            	}).collect(Collectors.toList());
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching UserTasks by UserVedio ID", ex);
        }
    }

    public UserTaskDto find(int id) {
        try {
            Optional<UserTask> userTaskOpt = repos.findById(id);
            return userTaskOpt.map(UserTaskMapper.Instance::toDto)
                              .orElseThrow(() -> new RuntimeException("UserTask with id " + id + " not found"));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching the UserTask with id " + id, ex);
        }
    }
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
