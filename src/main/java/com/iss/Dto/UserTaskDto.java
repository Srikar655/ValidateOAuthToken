package com.iss.Dto;

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
    private SubscriptionStatus subscriptionStatus = SubscriptionStatus.PENDING;

    public enum SubscriptionStatus {
        PENDING,
        ACTIVE,
        CANCELLED,
        COMPLETED
    }
}
