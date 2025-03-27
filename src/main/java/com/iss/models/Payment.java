package com.iss.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iss.constrains.CourseVideoTaskConstraint;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CourseVideoTaskConstraint
public class Payment {

    @Id
    @GeneratedValue
    private int paymentId;

    @ManyToOne
    @JsonBackReference
    @Basic(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false, referencedColumnName="id")
    private User user;
    
    @ManyToOne
    @JsonBackReference
    @Basic(fetch=FetchType.LAZY)
    @JoinColumn(name="usercourse_id", nullable=true, referencedColumnName="id")
    private UserCourse usercourse;
    
    @ManyToOne
    @JsonBackReference
    @Basic(fetch=FetchType.LAZY)
    @JoinColumn(name="uservideo_id", nullable=true, referencedColumnName="id")
    private UserVedio uservedio;

    @ManyToOne
    @JsonBackReference
    @Basic(fetch=FetchType.LAZY)
    @JoinColumn(name="usertask_id", nullable=true, referencedColumnName="id")
    private UserTask usertask;
    
    @Column(nullable = false)
    private BigDecimal amount;  // Stores the total amount of the transaction

    @Column(nullable = false)
    private String currency;  // Currency used for the transaction, e.g., INR
    @Column(nullable = false)
    private BigDecimal fee;  // Processing fee charged by the payment gateway

    @Column(nullable = false)
    private BigDecimal tax;  // Tax charged on the processing fee

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;  // Status of the payment

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name="payment_for")
    private PaymentType paymentFor;  // Payment purpose, like course, task, etc.

    @Column(nullable = false)
    private LocalDateTime paymentDate;  // Date and time of payment creation

    @Column(nullable = false, unique = true)
    private String paymentIdRazorpay;  // Razorpay unique payment ID

    @Column(nullable = true)
    private String orderId;  // Razorpay order ID for the payment

    @Column(nullable = true)
    private String paymentMethod;  // The method used for payment (e.g., UPI, card)

    @Column(nullable = true)
    private String vpa;  // For UPI payments, store Virtual Payment Address (if applicable)

    @Column(nullable = true)
    private String contact;  // User's contact number for payment record

    @Column(nullable = true)
    private String email;  // User's email for the payment record

    @Column(nullable = true)
    private String acquirerTransactionId;  // Transaction ID from the acquirer (e.g., UPI transaction ID)

    @Column(nullable = true)
    private String rrn;  // Reference Retrieval Number for payment tracking

    @Column(nullable = true)
    private String description;  // Description or purpose of the transaction

    
    public enum PaymentType {
        COURSE,
        EPISODE,
        TASK
    }
}
