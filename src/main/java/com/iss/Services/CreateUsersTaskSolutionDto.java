package com.iss.Services;


import jakarta.persistence.Lob;

public class CreateUsersTaskSolutionDto {
	int id;
    int userTaskId;
    @Lob
    String description;
    String submissionUrl;
    @Lob
    String submissionText;
    String email;
    
    
    
	public CreateUsersTaskSolutionDto() {
		super();
	}
	public CreateUsersTaskSolutionDto(int id, int userTaskId,String description,
			String submissionUrl, String submissionText) {
		super();
		this.id = id;
		this.userTaskId = userTaskId;
		this.description = description;
		this.submissionUrl = submissionUrl;
		this.submissionText = submissionText;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserTaskId() {
		return userTaskId;
	}
	public void setUserTaskId(int userTaskId) {
		this.userTaskId = userTaskId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubmissionUrl() {
		return submissionUrl;
	}
	public void setSubmissionUrl(String submissionUrl) {
		this.submissionUrl = submissionUrl;
	}
	public String getSubmissionText() {
		return submissionText;
	}
	public void setSubmissionText(String submissionText) {
		this.submissionText = submissionText;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
    

}
