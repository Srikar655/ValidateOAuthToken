package com.iss.models;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString(exclude= {"taskimages"})
@Builder
public class Tasks {
	@Id
	@GeneratedValue
	private int id;
	private String task;
	private String taskurl;
	@OneToMany(mappedBy="task",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	@JsonManagedReference
	private List<TaskImages> taskimages;
	private double taskprice;
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name="video_id",nullable=false,referencedColumnName="id")
	private Vedio video;
	

}
