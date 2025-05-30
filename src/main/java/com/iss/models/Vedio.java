package com.iss.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "tasks")
public class Vedio {
	@Id
	@GeneratedValue
	private int id;
	private String videourl;
	private double vedioprice;
	private String vediotitle;
	private String vediodescription;
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference("course-videos")
	@JoinColumn(name="course_id",nullable=false,referencedColumnName="id")
	private Course course;
	@OneToMany(mappedBy="video",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	@JsonManagedReference("video-tasks")
	private List<Tasks> tasks;
	@JsonManagedReference("video-uservedios")
	@OneToMany(mappedBy="vedio",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	private List<UserVedio> uservedio;
	
}
