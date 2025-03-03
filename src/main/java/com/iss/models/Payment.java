package com.iss.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue
    private int paymentId;

    @ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference
	@Basic(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable=false,referencedColumnName="id")
    private User user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name="payment_for")
    private PaymentType paymentFor; 

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED
    }

    public enum PaymentType {
    	COURSE,
        EPISODE,
        TASK
    }
}
