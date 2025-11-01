package com.iss.Dto;

import java.util.List;

import com.iss.models.SubscriptionStatus;



public class UserCourseDisplayDto {
	private int id;
	private int courseId;
	private String coursename;
	private double courseprice;
	private String courseTrailer;
	private List<String> courseFeatures;
	private String coursethumbnail;
	private String courseDescription;
	private int courseCategoryId;
	private SubscriptionStatus subscriptionStatus;
	public UserCourseDisplayDto(int id, int courseId, String coursename, double courseprice, String courseTrailer,
			String coursethumbnail, String courseDescription,
			SubscriptionStatus subscriptionStatus,int courseCategoryId) {
		super();
		this.id = id;
		this.courseId = courseId;
		this.coursename = coursename;
		this.courseprice = courseprice;
		this.courseTrailer = courseTrailer;
		this.coursethumbnail = coursethumbnail;
		this.courseDescription = courseDescription;
		this.subscriptionStatus = subscriptionStatus;
		this.courseCategoryId = courseCategoryId;
	}
	public UserCourseDisplayDto(int courseId, String coursename, double courseprice, String courseTrailer,
			String coursethumbnail, String courseDescription,int courseCategoryId) {
		super();
		this.courseId = courseId;
		this.coursename = coursename;
		this.courseprice = courseprice;
		this.courseTrailer = courseTrailer;
		this.coursethumbnail = coursethumbnail;
		this.courseDescription = courseDescription;
		this.courseCategoryId = courseCategoryId;
		this.subscriptionStatus = SubscriptionStatus.PENDING;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
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
	public String getCourseTrailer() {
		return courseTrailer;
	}
	public void setCourseTrailer(String courseTrailer) {
		this.courseTrailer = courseTrailer;
	}
	public List<String> getCourseFeatures() {
		return courseFeatures;
	}
	public void setCourseFeatures(List<String> courseFeatures) {
		this.courseFeatures = courseFeatures;
	}
	public String getCoursethumbnail() {
		return coursethumbnail;
	}
	public void setCoursethumbnail(String coursethumbnail) {
		this.coursethumbnail = coursethumbnail;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public SubscriptionStatus getSubscriptionStatus() {
		return subscriptionStatus;
	}
	public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}
	public int getCourseCategoryId() {
		return courseCategoryId;
	}
	public void setCourseCategoryId(int courseCategoryId) {
		this.courseCategoryId = courseCategoryId;
	}
	
	
	
	

	

	
	
	
}
