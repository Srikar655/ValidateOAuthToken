package com.iss.Dto;

import com.iss.models.PaymentFor;
import com.iss.models.PaymentStatus;

public class VideoPayementVerificationDto {
	public int id;
	public String url;
	public PaymentFor paymentFor;
	public PaymentStatus paymentStatus;
	public String status;
	
	public VideoPayementVerificationDto(int id, String url, PaymentFor paymentFor, PaymentStatus paymentStatus,
			String status) {
		super();
		this.id = id;
		this.url = url;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PaymentFor getPaymentFor() {
		return paymentFor;
	}

	public void setPaymentFor(PaymentFor paymentFor) {
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
