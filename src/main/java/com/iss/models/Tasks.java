package com.iss.models;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude= {"taskimages","usertask"})
@Builder
public class Tasks {
	@Id
	@GeneratedValue
	private int id;
	private String task;
	private String taskurl;
	private TaskLevel taskLevel;
	@Column(nullable = false, columnDefinition = "int default 0")
	private int points;
	@OneToMany(mappedBy="task",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	@JsonManagedReference("task-taskimages")
	private List<TaskImages> taskimages;
	private String gitHubReferenceUrl;
	private String pdfResourceUrl;
	@Column(length=1000)
	private String taskDescription;
	private double taskprice;
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'IMAGE_UPLOAD'")
    private SubmissionType submissionType;
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference("video-tasks")
	@JoinColumn(name="video_id",nullable=false,referencedColumnName="id")
	private Vedio video;
	@JsonManagedReference("task-usertasks")
	@OneToMany(mappedBy="task",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	private List<UserTask> usertask;

}
