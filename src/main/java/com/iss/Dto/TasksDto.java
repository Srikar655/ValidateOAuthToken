package com.iss.Dto;

import java.util.List;

import com.iss.models.Vedio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TasksDto {
	private int id;
	private String task;
	private String taskurl;
	private double taskprice;
	private List<TaskImageDto> taskimage;
	private Vedio video;

}
