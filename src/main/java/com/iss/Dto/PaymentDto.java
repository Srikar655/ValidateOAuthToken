package com.iss.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private int paymentId;

    private UserDto user;


    private BigDecimal amount;

    
    private PaymentStatus paymentStatus;

    private PaymentType paymentFor; 


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
    
    private String PaymenName;

    
}
