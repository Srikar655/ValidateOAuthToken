package com.iss.Dto;



import com.iss.models.Tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskImageDto {
	private int id;
	private byte[] taskImage;
	private Tasks task;
}
