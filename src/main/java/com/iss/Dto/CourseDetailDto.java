package com.iss.Dto;

import java.util.List;
import lombok.Builder;
@Builder
public class CourseDetailDto {
	private int id;
	private String coursename;
	private double courseprice;
	private String coursethumbnail;
	private String courseTrailer;
	private String courseDescription;
	private List<String> courseFeatures;
	private int categoryId;
	public CourseDetailDto(int id, String coursename, double courseprice, String coursethumbnail,
			String courseTrailer, String courseDescription, List<String> courseFeatures, 
			int categoryId) {
		super();
		this.id = id;
		this.coursename = coursename;
		this.courseprice = courseprice;
		this.coursethumbnail = coursethumbnail;
		this.courseTrailer = courseTrailer;
		this.courseDescription = courseDescription;
		this.courseFeatures = courseFeatures;
		this.categoryId = categoryId;
	}
	public CourseDetailDto() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public double getCourseprice() {
		return courseprice;
	}
	public void setCourseprice(double courseprice) {
		this.courseprice = courseprice;
	}
	public String getCoursethumbnail() {
		return coursethumbnail;
	}
	public void setCoursethumbnail(String coursethumbnail) {
		this.coursethumbnail = coursethumbnail;
	}
	public String getCourseTrailer() {
		return courseTrailer;
	}
	public void setCourseTrailer(String courseTrailer) {
		this.courseTrailer = courseTrailer;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public List<String> getCourseFeatures() {
		return courseFeatures;
	}
	public void setCourseFeatures(List<String> courseFeatures) {
		this.courseFeatures = courseFeatures;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	
	
}
