package com.iss.Dto;

import java.util.List;

import com.iss.models.AccessStatus;
import com.iss.models.Payment;
import com.iss.models.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVedioDto {

    private int id;

    private VideoDto vedio;
    
	private UserCourseDto usercourse;
    

	List<UserTaskDto> usertask;

    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    
    private List<Payment> payments;
    

    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessStatus accessStatus = AccessStatus.LOCKED;
}

