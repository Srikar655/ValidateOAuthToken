package com.iss.Services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.iss.Dto.UserTaskSolutionDto;
import com.iss.Dto.UserTaskSolutionImagesDto;
import com.iss.Mappers.UserTaskSolutionMapper;
import com.iss.Repos.UserTaskSolutionRepos;
import com.iss.models.UsersTaskSolution;
import com.iss.models.TaskProgress;
import com.iss.models.UserTask;



@Service
public class UserTaskSolutionService {
	private final UserTaskSolutionRepos repos;
	private final UserTaskService userTaskService;
	public UserTaskSolutionService(UserTaskSolutionRepos repos,UserTaskService userTaskService)
	{
		this.repos=repos;
		this.userTaskService=userTaskService;
	}
	public UserTaskSolutionDto add(UsersTaskSolution userTaskSolution)
	{
		try
		{
			UserTask userTask=this.userTaskService.findAndGetUserTask(userTaskSolution.getUsertask().getId());
			if(userTask!=null)
			{
				userTask.setTaskProgress(TaskProgress.SUBMITED);
				this.userTaskService.update(userTask);
				UsersTaskSolution newUserTaskSolution=repos.save(userTaskSolution);
				return UserTaskSolutionMapper.Instance.toDto(newUserTaskSolution);
			}
			return null;
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public List<UserTaskSolutionDto> findAll()
	{
		try
		{
			return UserTaskSolutionMapper.Instance.toDtoList(repos.findAll());
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public List<UserTaskSolutionDto> findAllSubmittedSolutions(Pageable pageable)
	{
		try
		{
			Optional<Page<UsersTaskSolution>> optionalSubmittedUserTaskSolutions=repos.getSubmittedSolutions(TaskProgress.SUBMITED,pageable);
			if(optionalSubmittedUserTaskSolutions.isPresent())
			{
				List<UserTaskSolutionDto> dtolist= UserTaskSolutionMapper.Instance.toDtoList(optionalSubmittedUserTaskSolutions.get().getContent());
				dtolist.forEach(us->System.out.println(us.getUsertask().getTask().getTask()));
				return dtolist;
			}
			return null;
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public UserTaskSolutionDto find(int id)
	{
		try
		{
			return UserTaskSolutionMapper.Instance.toDto(repos.findById(id).get());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public void delete(int id)
	{
		try
		{
			repos.deleteById(id);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public UserTaskSolutionDto updateSuccessReview(UsersTaskSolution userTaskSolution)
	{
		UserTask userTask=this.userTaskService.findAndGetUserTask(userTaskSolution.getUsertask().getId());
		if(userTask!=null)
		{
			Optional<UsersTaskSolution> optionalUserTaskSolution=repos.findById(userTaskSolution.getId());
			if(optionalUserTaskSolution.isPresent())
			{
				userTaskSolution.setReviewedAt(new Timestamp(System.currentTimeMillis()));
				userTask.setTaskProgress(userTaskSolution.getUsertask().getTaskProgress());
				
			}
			return null;
		}
		return null;
	}
	public UserTaskSolutionImagesDto findSolutionImages(int userSolutionId) {
		try {
			Optional<List<byte[]>> optionallist=repos.findSolutionImagesById(userSolutionId);
			if(optionallist.isPresent())
			{
				return UserTaskSolutionImagesDto.builder().solutionimages(optionallist.get()).build();
			}
			return null;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
