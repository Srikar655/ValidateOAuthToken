package com.iss.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {
	private int id;
	private String coursename;
	private double courseprice;
	private byte[] coursethumbnail;
}
