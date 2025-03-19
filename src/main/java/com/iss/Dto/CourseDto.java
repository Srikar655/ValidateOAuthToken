package com.iss.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.List;

import com.iss.models.CourseCategory;
import com.iss.models.UserCourse;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {
	private int id;
	private String coursename;
	private double courseprice;
	private byte[] coursethumbnail;
	private List<VideoDto> vedios;
	private String courseTrailer;
	private String courseDescription;
	private List<String> courseFeatures;
	private List<UserCourse> usercourse;
	private CourseCategory courseCategory;

}
