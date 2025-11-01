package com.iss.Dto;

public class VideoUpdateDto {
	private int id;
	private String videoUrl;
	private String videoTitle;
	private double videoPrice;
	private String videoDescription;
	
	
	public VideoUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VideoUpdateDto(int id, String videoUrl, String videoTitle, double videoPrice, String videoDescription) {
		super();
		this.id = id;
		this.videoUrl = videoUrl;
		this.videoTitle = videoTitle;
		this.videoPrice = videoPrice;
		this.videoDescription = videoDescription;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	public double getVideoPrice() {
		return videoPrice;
	}
	public void setVideoPrice(double videoPrice) {
		this.videoPrice = videoPrice;
	}
	public String getVideoDescription() {
		return videoDescription;
	}
	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}
	
	
}
