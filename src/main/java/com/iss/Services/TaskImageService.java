package com.iss.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iss.Dto.TaskImageDto;
import com.iss.Mappers.TaskImageMapper;
import com.iss.Repos.TaskImageReps;
import com.iss.models.TaskImages;

@Service
public class TaskImageService {
	private final TaskImageReps repos;
	public TaskImageService(TaskImageReps repos)
	{
		this.repos=repos;
	}
	public void add(TaskImages image)
	{
		repos.save(image);
	}
	public TaskImageDto find(int id)
	{
		return TaskImageMapper.Instance.toDto(repos.findById(id).get());
	}
	public List<TaskImageDto> findAll()
	{
		return TaskImageMapper.Instance.toDtoList(repos.findAll());
	}
	public List<TaskImageDto> findByTaskId(int id)
	{
		return TaskImageMapper.Instance.toDtoList(repos.findByTaskId(id));
	}
}
