package com.iss.Dto;

public class VideoCreateDto {
	private String videoTitle;
	private String videoDescription;
	private double videoPrice;
    private String videoUrl;
    private int courseId;
	public VideoCreateDto(String videoTitle, String videoDescription, double videoPrice, String videoUrl,int courseId) {
		super();
		this.videoTitle = videoTitle;
		this.videoDescription = videoDescription;
		this.videoPrice = videoPrice;
		this.videoUrl = videoUrl;
		this.courseId = courseId;
	}
	public VideoCreateDto() {
		super();
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
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
}
