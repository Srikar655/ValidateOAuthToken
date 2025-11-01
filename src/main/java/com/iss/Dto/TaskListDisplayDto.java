package com.iss.Dto;

import com.iss.models.TaskLevel;


public class TaskListDisplayDto {
	
	private int id;
	private String task;
	private TaskLevel taskLevel;
	private int points;
	
	
	
	public TaskListDisplayDto(int id,String task, TaskLevel taskLevel, int points) {
		super();
		this.id = id;
		this.task = task;
		this.taskLevel = taskLevel;
		this.points = points;
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

}
