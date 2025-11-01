package com.iss.Dto;

import com.iss.models.SubmissionType;
import com.iss.models.TaskLevel;

public class TaskCreateDto {
	private String task;
	private String taskDescription;
	private String pdfResourceUrl;
	private String gitHubReferenceUrl;
	private TaskLevel taskLevel;
	private double taskPrice;
	private int videoId;
	private String taskUrl;
	private int points;
	private SubmissionType submissionType;
	public TaskCreateDto(String task, String taskDescription, String pdfResourceUrl, String gitHubReferenceUrl,
			TaskLevel taskLevel, double taskPrice, int videoId, String taskUrl, int points,
			SubmissionType submissionType) {
		super();
		this.task = task;
		this.taskDescription = taskDescription;
		this.pdfResourceUrl = pdfResourceUrl;
		this.gitHubReferenceUrl = gitHubReferenceUrl;
		this.taskLevel = taskLevel;
		this.taskPrice = taskPrice;
		this.videoId = videoId;
		this.taskUrl = taskUrl;
		this.points = points;
		this.submissionType = submissionType;
	}
	public TaskCreateDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
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
	public TaskLevel getTaskLevel() {
		return taskLevel;
	}
	public void setTaskLevel(TaskLevel taskLevel) {
		this.taskLevel = taskLevel;
	}
	public double getTaskPrice() {
		return taskPrice;
	}
	public void setTaskPrice(double taskPrice) {
		this.taskPrice = taskPrice;
	}
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	public String getTaskUrl() {
		return taskUrl;
	}
	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public SubmissionType getSubmissionType() {
		return submissionType;
	}
	public void setSubmissionType(SubmissionType submissionType) {
		this.submissionType = submissionType;
	}
	
	
}
