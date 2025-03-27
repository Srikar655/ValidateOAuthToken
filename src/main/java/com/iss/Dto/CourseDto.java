package com.iss.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.List;

import com.iss.models.CourseCategory;
import com.iss.models.UserCourse;
import com.iss.models.Vedio;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {
	private int id;
	private String coursename;
	private double courseprice;
	private byte[] coursethumbnail;
	private List<Vedio> vedios;
	private String courseTrailer;
	private String courseDescription;
	private List<String> courseFeatures;
	private List<UserCourse> usercourse;
	private CourseCategory courseCategory;

}
