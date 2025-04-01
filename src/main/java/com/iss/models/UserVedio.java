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
public class UserVedio {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch=FetchType.EAGER)
	@JsonBackReference("video-uservedios")
	@JoinColumn(name="vedio_id",nullable=false,referencedColumnName="id")
    private Vedio vedio;
    
    @ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference("usercourse-uservedios")
	@JoinColumn(name="usercourse_id",nullable=false,referencedColumnName="id")
	private UserCourse usercourse;
    
    @JsonManagedReference("uservedio-usertasks")
	@OneToMany(mappedBy="uservedio",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	List<UserTask> usertask;
    
    @JsonManagedReference("uservedio-payments")
	@OneToMany(mappedBy="uservedio",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
	private List<Payment> payments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessStatus accessStatus = AccessStatus.LOCKED;

    
}
