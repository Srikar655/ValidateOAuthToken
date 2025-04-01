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
public class UserTask {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference("task-usertasks")
	@JoinColumn(name="tasks_id",nullable=false,referencedColumnName="id")
    private Tasks task;
    
    @ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference("uservedio-usertasks")
	@JoinColumn(name="uservedio_id",nullable=false,referencedColumnName="id")
	private UserVedio uservedio;
    
    @JsonManagedReference("usertask-payments")
	@OneToMany(mappedBy="usertask",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	private List<Payment> payments;
    
    @JsonManagedReference("usertask-usersolutions")
   	@OneToMany(mappedBy="usertask",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
   	private List<UsersTaskSolution> usersolution;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskProgress taskProgress = TaskProgress.PENDING;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessStatus accessStatus = AccessStatus.LOCKED;

    
}
