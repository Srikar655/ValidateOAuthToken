package com.iss.Dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDto {
	private int id;
	private String videourl;
	private double vedioprice;
	private String vediotitle;
	private String vediodescription;
	private CourseDto course;
	private List<TasksDto> tasks;
}
