package com.iss.Dto;



public class VideoListDisplayDto {
	private int id;
	private String videoTitle;
    
	
	public VideoListDisplayDto() {
		super();
	}
		
	public VideoListDisplayDto(int id, String videoTitle) {
		super();
		this.id = id;
		this.videoTitle = videoTitle;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	
	
}
