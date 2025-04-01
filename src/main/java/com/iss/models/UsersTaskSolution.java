package com.iss.models;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
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
	@ElementCollection(fetch=FetchType.LAZY)
	@Lob
	private List<byte[]> solutionimages;
	private Timestamp submittedAt;
	private Timestamp reviewedAt;
	private String description;
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference("usertask-usersolutions")
	@JoinColumn(name="usertask_id",nullable=false,referencedColumnName="id")
	private UserTask usertask;
	private String email;
}
