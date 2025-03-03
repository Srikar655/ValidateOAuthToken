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
public class UserCourseDto {


    private int id;

    private UserDto user;
    private CourseDto course;
    
	List<UserVedioDto> userVedios;
    private SubscriptionStatus subscriptionStatus = SubscriptionStatus.PENDING;

    public enum SubscriptionStatus {
        PENDING,
        ACTIVE,
        CANCELLED,
        COMPLETED
    }
}

