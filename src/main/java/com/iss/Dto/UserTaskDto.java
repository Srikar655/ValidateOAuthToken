package com.iss.Dto;

import com.iss.models.PaymentStatus;

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


}
