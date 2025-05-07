package com.iss.Dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.iss.models.Notifications;
import com.iss.models.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private int id;
    private String username;
    private String email;

    private String password;
    private String Picture;
    private String phonenumber;
    private Set<Role> role ;
    private Timestamp createdAt;
	List<UserCourseDto> usercourse;
    
	private List<PaymentDto> payments;
	private List<Notifications> notifications;
}
