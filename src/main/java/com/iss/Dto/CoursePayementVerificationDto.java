package com.iss.Dto;

import com.iss.models.PaymentFor;
import com.iss.models.SubscriptionStatus;

public class CoursePayementVerificationDto {
	public int id;
	public int resourceId;
	public PaymentFor paymentFor;
	public SubscriptionStatus subscriptionStatus;
	public String status;
	public CoursePayementVerificationDto(int id,int resourceId, PaymentFor paymentFor, SubscriptionStatus subscriptionStatus,
			String status) {
		super();
		this.id = id;
		this.resourceId = resourceId;
		this.paymentFor = paymentFor;
		this.subscriptionStatus = subscriptionStatus;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public PaymentFor getPaymentFor() {
		return paymentFor;
	}
	public void setPaymentFor(PaymentFor paymentFor) {
		this.paymentFor = paymentFor;
	}
	public SubscriptionStatus getSubscriptionStatus() {
		return subscriptionStatus;
	}
	public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
