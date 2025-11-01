package com.iss.Dto;




import java.sql.Timestamp;

import com.iss.models.CorrectionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
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
	@Lob
	private String solutionimages;
	@Lob 
	private String submissionText;
	@Column(length = 2048)
	private String submissionUrl;
	private Timestamp submittedAt;
	private Timestamp reviewedAt;
	private String description;
	private int userTaskId;
	private String email;
	private CorrectionStatus correctionStatus;
	
}
