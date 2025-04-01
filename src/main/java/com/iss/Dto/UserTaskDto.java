package com.iss.Dto;

import java.util.List;

import com.iss.models.AccessStatus;
import com.iss.models.Payment;
import com.iss.models.PaymentStatus;
import com.iss.models.TaskProgress;
import com.iss.models.Tasks;
import com.iss.models.UserVedio;
import com.iss.models.UsersTaskSolution;

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
public class UserTaskDto {
    private int id;

    private TasksDto task;
	private UserVedioDto uservedio;
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    private List<PaymentDto> payments;
	private List<UserTaskSolutionDto> usersolution;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskProgress taskProgress = TaskProgress.PENDING;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessStatus accessStatus = AccessStatus.LOCKED;
}
