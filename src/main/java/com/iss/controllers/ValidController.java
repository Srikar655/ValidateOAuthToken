package com.iss.controllers;


import java.sql.Timestamp;
import java.util.Set;



import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.iss.models.User;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iss.Repos.RoleRepository;
import com.iss.Services.CustomUserDetailsService;




@RestController

@RequestMapping("/api")
public class ValidController
{
	private final CustomUserDetailsService userService;
	private final RoleRepository roleRepos;

	public ValidController(CustomUserDetailsService userService,RoleRepository roleRepos) {
		super();
		this.userService = userService;
		this.roleRepos = roleRepos;
	}
	@GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal Jwt jwt) {

		String email = jwt.getClaim("email");
        UserDetails user;

        try {
            user = this.userService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {

        	String name= jwt.getClaimAsString("name");
    		String picture=jwt.getClaimAsString("picture");
    		User u=User.builder().email(email).username(name).Picture(picture).createdAt(new Timestamp(System.currentTimeMillis())).roles(Set.of(roleRepos.findByName("ROLE_USER"))).build();
			user=userService.add(u);
        }

        return ResponseEntity.ok(user);
    }
	@GetMapping("/login")
    public ResponseEntity<?> login(@AuthenticationPrincipal Jwt jwt) {
		
		 
		String email = jwt.getClaim("email");
        UserDetails user;

        try {
            user = this.userService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {

        	String name= jwt.getClaimAsString("name");
    		String picture=jwt.getClaimAsString("picture");
    		User u=User.builder().email(email).username(name).Picture(picture).createdAt(new Timestamp(System.currentTimeMillis())).roles(Set.of(roleRepos.findByName("ROLE_USER"))).build();
			user=userService.add(u);
        }

        return ResponseEntity.ok(user);
         
         
    }	
}