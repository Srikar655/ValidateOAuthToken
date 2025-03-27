package com.iss.Dto;

import java.util.List;

import com.iss.models.UserVedio;
import com.iss.models.Course;
import com.iss.models.Tasks;
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
	private Course course;
	private List<Tasks> tasks;
	private List<UserVedio> uservedio;
}
