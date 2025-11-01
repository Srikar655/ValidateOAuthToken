package com.iss.Dto;

public class CourseCreateDto {
	private String courseName;
	private double coursePrice;
	private String courseDescription;
	private String courseTrailer;
	private int courseCategoryId;
	public CourseCreateDto(String courseName, double coursePrice, String courseTrailer,
			int courseCategoryId,String courseDescription) {
		super();
		this.courseName = courseName;
		this.coursePrice = coursePrice;
		this.courseTrailer = courseTrailer;
		this.courseCategoryId = courseCategoryId;
		this.courseDescription = courseDescription;
	}
	public CourseCreateDto() {
		super();
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public double getCoursePrice() {
		return coursePrice;
	}
	public void setCoursePrice(double coursePrice) {
		this.coursePrice = coursePrice;
	}
	public String getCourseTrailer() {
		return courseTrailer;
	}
	public void setCourseTrailer(String courseTrailer) {
		this.courseTrailer = courseTrailer;
	}

	public int getCourseCategoryId() {
		return courseCategoryId;
	}
	public void setCourseCategoryId(int courseCategoryId) {
		this.courseCategoryId = courseCategoryId;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	
	
	
}
