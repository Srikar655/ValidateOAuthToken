package com.iss.Services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iss.Dto.TasksDto;
import com.iss.Mappers.TasksMapper;
import com.iss.Repos.TasksRepository;

import com.iss.models.Tasks;

@Service
public class TasksService {
	private final TasksRepository repos;
	public TasksService(TasksRepository repos)
	{
		this.repos=repos;
	}
	public TasksDto add(Tasks task)
	{
		return TasksMapper.Instance.toDto(repos.save(task));
	}
	public List<TasksDto> add(List<Tasks> tasks)
	{
		return TasksMapper.Instance.toDtoList(repos.saveAllAndFlush(tasks));
	}
	public List<TasksDto> findAll()
	{
		return TasksMapper.Instance.toDtoList(repos.findAll());
	}
	public TasksDto find(int id)
	{
		return TasksMapper.Instance.toDto(repos.findById(id).get());
	}
	public List<TasksDto> findByVideoId(int videoId, Pageable pageable) {
	    List<Tasks> tasks= repos.findByVideoId(videoId, pageable).getContent();
	    return TasksMapper.Instance.toDtoList(tasks);
	}
	public TasksDto update(Tasks task)
	{
		return TasksMapper.Instance.toDto(repos.save(task));
	}
	public void delete(int id)
	{
		 repos.deleteById(id);
	}
}
