package com.iss.Dto;

import java.util.List;

public class CourseFeaturesUpdateDto {
	private int id;
	private List<String> courseFeatures;
	private String courseTrailer;
	public CourseFeaturesUpdateDto(int id, List<String> courseFeatures, String courseTrailer) {
		super();
		this.id = id;
		this.courseFeatures = courseFeatures;
		this.courseTrailer = courseTrailer;
	}
	public CourseFeaturesUpdateDto() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getCourseFeatures() {
		return courseFeatures;
	}
	public void setCourseFeatures(List<String> courseFeatures) {
		this.courseFeatures = courseFeatures;
	}
	public String getCourseTrailer() {
		return courseTrailer;
	}
	public void setCourseTrailer(String courseTrailer) {
		this.courseTrailer = courseTrailer;
	}
	
	
}
