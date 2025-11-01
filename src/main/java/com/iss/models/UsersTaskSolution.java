package com.iss.models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersTaskSolution {
	@Id
	@GeneratedValue
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
	@Lob
	private byte[] audioAdvice;
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference("usertask-usersolutions")
	@JoinColumn(name="usertask_id",nullable=false,referencedColumnName="id")
	private UserTask usertask;
	private String email;
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private CorrectionStatus correctionStatus;
}
