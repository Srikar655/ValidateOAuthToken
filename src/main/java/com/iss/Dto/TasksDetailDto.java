package com.iss.Dto;

import com.iss.models.SubmissionType;
import com.iss.models.TaskLevel;
import lombok.Builder;

@Builder
public class TasksDetailDto {
	private int id;
	private String task;
	private String taskUrl;
	private double taskPrice;
	private TaskLevel taskLevel;
	private int points;
	private String pdfResourceUrl;
	private String gitHubReferenceUrl;
	private SubmissionType submissionType;
	private String taskDescription;
	public TasksDetailDto(int id, String task, String taskUrl, double taskPrice, TaskLevel taskLevel, int points,
			String pdfResourceUrl, String gitHubReferenceUrl, SubmissionType submissionType,String taskDescription) {
		super();
		this.id = id;
		this.task = task;
		this.taskUrl = taskUrl;
		this.taskPrice = taskPrice;
		this.taskLevel = taskLevel;
		this.points = points;
		this.pdfResourceUrl = pdfResourceUrl;
		this.gitHubReferenceUrl = gitHubReferenceUrl;
		this.submissionType = submissionType;
		this.taskDescription = taskDescription;
	}
	public TasksDetailDto() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getTaskUrl() {
		return taskUrl;
	}
	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}
	public double getTaskPrice() {
		return taskPrice;
	}
	public void setTaskPrice(double taskPrice) {
		this.taskPrice = taskPrice;
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
	public String getPdfResourceUrl() {
		return pdfResourceUrl;
	}
	public void setPdfResourceUrl(String pdfResourceUrl) {
		this.pdfResourceUrl = pdfResourceUrl;
	}
	public String getGitHubReferenceUrl() {
		return gitHubReferenceUrl;
	}
	public void setGitHubReferenceUrl(String gitHubReferenceUrl) {
		this.gitHubReferenceUrl = gitHubReferenceUrl;
	}
	public SubmissionType getSubmissionType() {
		return submissionType;
	}
	public void setSubmissionType(SubmissionType submissionType) {
		this.submissionType = submissionType;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	
	
	
}
