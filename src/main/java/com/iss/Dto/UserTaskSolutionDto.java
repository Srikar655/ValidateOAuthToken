package com.iss.Dto;


import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTaskSolutionDto {
	private int id;
	private List<byte[]> solutionimages;
	
	private String description;
	private UserTaskDto usertask;
	private String email;
	
}
