package com.iss.controllers;


import java.sql.Timestamp;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.iss.models.EmailDetails;
import com.iss.models.User;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iss.Repos.RoleRepository;
import com.iss.Services.CustomUserDetailsService;
import com.iss.Services.EmailService;




@RestController

@RequestMapping("/api")
public class ValidController
{
	private final CustomUserDetailsService userService;
	private final RoleRepository roleRepos;
	private final EmailService emailService;
	
	@Value("${loginmessage}")
	private String message;

	public ValidController(CustomUserDetailsService userService,RoleRepository roleRepos,EmailService emailService) {
		super();
		this.userService = userService;
		this.roleRepos = roleRepos;
		this.emailService=emailService;
	}
	@GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal Jwt jwt) {

		String email = jwt.getClaim("email");
        UserDetails user;

        try {
            user = this.userService.loadUserByUsername(email);
            if(user==null)
            {
            	String name= jwt.getClaimAsString("name");
        		String picture=jwt.getClaimAsString("picture");
        		User u=User.builder().email(email).username(name).Picture(picture).createdAt(new Timestamp(System.currentTimeMillis())).roles(Set.of(roleRepos.findByName("ROLE_USER"))).build();
    			user=userService.add(u);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(500).body("Error in the userinfo: " + e.getMessage());
        }

        return ResponseEntity.ok(user);
    }
	@GetMapping("/login")
    public ResponseEntity<?> login(@AuthenticationPrincipal Jwt jwt) {
		
		 
		String email = jwt.getClaim("email");
        UserDetails user;

        try {
            user = this.userService.loadUserByUsername(email);
            if(user==null)
            {
            	String name= jwt.getClaimAsString("name");
        		String picture=jwt.getClaimAsString("picture");
        		User u=User.builder().email(email).username(name).Picture(picture).createdAt(new Timestamp(System.currentTimeMillis())).roles(Set.of(roleRepos.findByName("ROLE_USER"))).build();
    			user=userService.add(u);
            }
            EmailDetails em = new EmailDetails();
            em.setRecipient(email); 
            em.setSubject("Welcome to Innovative Tutorials - You're All Set to Start Your Learning Journey!");

            em.setMsgBody(message.formatted(email));
            System.out.println("Sending");

            emailService.sendSimpleMail(em);
        } catch (Exception e) {
        
        	e.printStackTrace();
        	return ResponseEntity.status(500).body("Error logging the user: " + e.getMessage());
        }

        return ResponseEntity.ok(user);
         
         
    }	
}