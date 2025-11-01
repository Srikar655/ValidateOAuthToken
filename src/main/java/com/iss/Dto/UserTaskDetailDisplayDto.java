package com.iss.Dto;

import com.iss.models.AccessStatus;
import com.iss.models.PaymentStatus;
import com.iss.models.SubmissionType;
import com.iss.models.TaskLevel;
import com.iss.models.TaskProgress;

public class UserTaskDetailDisplayDto {
	private int id;
	private int taskId;
	private String task;
	private String taskUrl;
	private double taskPrice;
	private TaskLevel taskLevel;
	private int points;
	private String pdfResourceUrl;
	private String gitHubReferenceUrl;
	private PaymentStatus paymentStatus;
	private AccessStatus accessStatus;
	private TaskProgress taskProgress;
	private SubmissionType submissionType;
	public UserTaskDetailDisplayDto(int id, int taskId, String task, String taskUrl, double taskPrice,
			TaskLevel taskLevel, int points, String pdfResourceUrl, String gitHubReferenceUrl,PaymentStatus paymentStatus,
			AccessStatus accessStatus, TaskProgress taskProgress) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.task = task;
		this.taskUrl = taskUrl;
		this.taskPrice = taskPrice;
		this.taskLevel = taskLevel;
		this.points = points;
		this.pdfResourceUrl = pdfResourceUrl;
		this.gitHubReferenceUrl = gitHubReferenceUrl;
		this.paymentStatus = paymentStatus;
		this.accessStatus = accessStatus;
		this.taskProgress = taskProgress;
	}
	public UserTaskDetailDisplayDto(int id, int taskId, String task, double taskPrice, TaskLevel taskLevel, int points,
			PaymentStatus paymentStatus, AccessStatus accessStatus,
			TaskProgress taskProgress) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.task = task;
		this.taskPrice = taskPrice;
		this.taskLevel = taskLevel;
		this.points = points;
		this.paymentStatus = paymentStatus;
		this.accessStatus = accessStatus;
		this.taskProgress = taskProgress;
	}
	public UserTaskDetailDisplayDto() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
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
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public AccessStatus getAccessStatus() {
		return accessStatus;
	}
	public void setAccessStatus(AccessStatus accessStatus) {
		this.accessStatus = accessStatus;
	}
	public TaskProgress getTaskProgress() {
		return taskProgress;
	}
	public void setTaskProgress(TaskProgress taskProgress) {
		this.taskProgress = taskProgress;
	}
	public SubmissionType getSubmissionType() {
		return submissionType;
	}
	public void setSubmissionType(SubmissionType submissionType) {
		this.submissionType = submissionType;
	}
}
