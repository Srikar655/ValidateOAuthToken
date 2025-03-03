package com.iss.Dto;

import java.util.List;


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

    private SubscriptionStatus subscriptionStatus = SubscriptionStatus.PENDING;

    public enum SubscriptionStatus {
        PENDING,
        ACTIVE,
        CANCELLED,
        COMPLETED 
    }
}

