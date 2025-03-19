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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCourse {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference
    @JoinColumn(name = "user_id", nullable = false,referencedColumnName="id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference("course-usercourses")
	@JoinColumn(name="course_id",nullable=false,referencedColumnName="id")
    private Course course;
    
    @JsonManagedReference
	@OneToMany(mappedBy="usercourse",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	List<UserVedio> userVedios;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus subscriptionStatus = SubscriptionStatus.PENDING;

    public enum SubscriptionStatus {
        PENDING,
        ACTIVE,
        CANCELLED,
        COMPLETED
    }
}
