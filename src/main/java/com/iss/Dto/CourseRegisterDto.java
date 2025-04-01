package com.iss.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRegisterDto {
	private int id;
	private String coursename;
	private double courseprice;
	private List<VideoDto> vedios;
}
