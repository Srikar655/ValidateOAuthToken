package com.iss.Dto;

import com.iss.models.AccessStatus;
import com.iss.models.TaskLevel;
import com.iss.models.TaskProgress;

public class UserTaskListDisplayDto {
	
	private int id;
	private int taskId;
	private String task;
	private TaskLevel taskLevel;
	private int points;
	private TaskProgress taskProgress;
	private AccessStatus accessStatus;
	public UserTaskListDisplayDto(int id,int taskId, String task,TaskLevel taskLevel,int points,TaskProgress taskProgress,AccessStatus accessStatus) {
		super();
		this.id = id;
		this.task = task;
		this.taskLevel = taskLevel;
		this.points = points;
		this.taskProgress = taskProgress;
		this.accessStatus = accessStatus;
		this.taskId = taskId;
	}
	public UserTaskListDisplayDto() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public TaskLevel getTaskLevel() {
		return taskLevel;
	}
	public void setTaskLevel(TaskLevel taskLevel) {
		this.taskLevel = taskLevel;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public TaskProgress getTaskProgress() {
		return taskProgress;
	}
	public void setTaskProgress(TaskProgress taskProgress) {
		this.taskProgress = taskProgress;
	}
	public AccessStatus getAccessStatus() {
		return accessStatus;
	}
	public void setAccessStatus(AccessStatus accessStatus) {
		this.accessStatus = accessStatus;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	
	
	
	

}
