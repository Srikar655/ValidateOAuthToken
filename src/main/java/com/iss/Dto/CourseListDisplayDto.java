package com.iss.Dto;


public class CourseListDisplayDto {
	
	private int id;
	private String coursename;
	private double courseprice;
	private int categoryId;
	private String coursethumbnail;

	public CourseListDisplayDto(int id, String coursename, double courseprice, int categoryId,String coursethumbnail) {
		this.id = id;
		this.coursename = coursename;
		this.courseprice = courseprice;
		this.categoryId = categoryId;
		this.coursethumbnail = coursethumbnail;
	}

	// Getters and Setters
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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCoursethumbnail() {
		return coursethumbnail;
	}

	public void setCoursethumbnail(String coursethumbnail) {
		this.coursethumbnail = coursethumbnail;
	}
	
	
	
}
