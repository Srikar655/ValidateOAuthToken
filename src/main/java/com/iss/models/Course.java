package com.iss.models;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@ToString(exclude = {"vedios","coursethumbnail"})
public class Course {
	@Id
	@GeneratedValue
	private int id;
	private String coursename;
	private double courseprice;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] coursethumbnail;
	@JsonManagedReference
	@OneToMany(mappedBy="course",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	List<Vedio> vedios;
	
	private String courseTrailer;
	@Column(length = 1000) 
	private String courseDescription;
	private List<String> courseFeatures;
}
