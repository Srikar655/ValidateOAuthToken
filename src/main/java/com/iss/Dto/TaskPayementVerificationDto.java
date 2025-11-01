package com.iss.Dto;

import com.iss.Dto.PaymentDto.PaymentType;
import com.iss.models.PaymentStatus;

public class TaskPayementVerificationDto {
	private int id;
    private String taskUrl;
    private String taskReferenceUrl;
    private PaymentType paymentFor;
    private PaymentStatus paymentStatus;
    private String status;
	public TaskPayementVerificationDto(int id, String taskUrl, String taskReferenceUrl, PaymentType paymentFor,
			PaymentStatus paymentStatus, String status) {
		super();
		this.id = id;
		this.taskUrl = taskUrl;
		this.taskReferenceUrl = taskReferenceUrl;
		this.paymentFor = paymentFor;
		this.paymentStatus = paymentStatus;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTaskUrl() {
		return taskUrl;
	}
	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}
	public String getTaskReferenceUrl() {
		return taskReferenceUrl;
	}
	public void setTaskReferenceUrl(String taskReferenceUrl) {
		this.taskReferenceUrl = taskReferenceUrl;
	}
	public PaymentType getPaymentFor() {
		return paymentFor;
	}
	public void setPaymentFor(PaymentType paymentFor) {
		this.paymentFor = paymentFor;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
