package com.iss.Dto;

import com.iss.models.AccessStatus;
import com.iss.models.PaymentStatus;
import com.iss.models.SubscriptionStatus;

public class UserVideoDetailDisplayDto {
	public int id;
	public int videoId;
	public String videoTitle;
	public String videoDescription;
	public double videoPrice;
	public String videoUrl;
	public AccessStatus accessStatus;
	public PaymentStatus paymentStatus;
	public UserVideoDetailDisplayDto(int id, int videoId, String videoTitle, String videoDescription, double videoPrice,
			String videoUrl, SubscriptionStatus subscriptionStatus, AccessStatus accessStatus,PaymentStatus paymentStatus) {
		super();
		this.id = id;
		this.videoId = videoId;
		this.videoTitle = videoTitle;
		this.videoDescription = videoDescription;
		this.videoPrice = videoPrice;
		this.videoUrl = videoUrl;
		this.accessStatus = accessStatus;
		this.paymentStatus = paymentStatus;
	}
	public UserVideoDetailDisplayDto(int id, int videoId, String videoTitle, String videoDescription, double videoPrice,
			 AccessStatus accessStatus,PaymentStatus paymentStatus) {
		super();
		this.id = id;
		this.videoId = videoId;
		this.videoTitle = videoTitle;
		this.videoDescription = videoDescription;
		this.videoPrice = videoPrice;
		this.accessStatus = accessStatus;
		this.paymentStatus = paymentStatus;
	}
	public UserVideoDetailDisplayDto(int videoId, String videoTitle, String videoDescription, double videoPrice) {
		super();
		this.videoId = videoId;
		this.videoTitle = videoTitle;
		this.videoDescription = videoDescription;
		this.videoPrice = videoPrice;
		this.accessStatus = AccessStatus.LOCKED;
		this.paymentStatus = PaymentStatus.PENDING;
	}
	public UserVideoDetailDisplayDto() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	public String getVideoDescription() {
		return videoDescription;
	}
	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}
	public double getVideoPrice() {
		return videoPrice;
	}
	public void setVideoPrice(double videoPrice) {
		this.videoPrice = videoPrice;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public AccessStatus getAccessStatus() {
		return accessStatus;
	}
	public void setAccessStatus(AccessStatus accessStatus) {
		this.accessStatus = accessStatus;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	
	
}
