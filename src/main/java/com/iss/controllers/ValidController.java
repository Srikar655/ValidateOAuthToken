package com.iss.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iss.Services.CustomUserDetailsService;


@RestController
@RequestMapping("/api")

public class ValidController
{

	private final CustomUserDetailsService userService;
	public ValidController(CustomUserDetailsService userService)
	{

		this.userService=userService;
	}
	@GetMapping("/userinfo")
    public ResponseEntity<Map<String, Object>> getUserInfo(@AuthenticationPrincipal Jwt jwt) {

        String email = jwt.getClaimAsString("email");
        String name = jwt.getClaimAsString("name");
        List<String> audience = jwt.getAudience();

        Map<String, Object> userInfo = Map.of(
            "email", email,
            "name", name,
            "audience",audience
        );

        return ResponseEntity.ok(userInfo);
    }
	@GetMapping("/login")
    public ResponseEntity<?> login(@AuthenticationPrincipal Jwt jwt) {
		
        return ResponseEntity.ok(userService.loadUserByUsername(jwt.getClaimAsString("email")));
    }	
}