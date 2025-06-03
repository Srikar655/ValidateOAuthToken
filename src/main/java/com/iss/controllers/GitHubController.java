package com.iss.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iss.Services.CustomUserDetailsService;
import com.iss.models.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController

public class GitHubController {

    @Value("${github-clientId}")
    private String clientId;

    @Value("${github-clientsecreat}")
    private String clientSecret;
    
    @Autowired
    private CustomUserDetailsService userService;

    @GetMapping("/gitHub/callback")
    public void gitHubCallBack(@RequestParam String code,@RequestParam String state, HttpServletResponse response) {
        try {
        	ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> stateMap = mapper.readValue(state, Map.class);
            String email = ""+stateMap.get("email");
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);  // ✅ must set this!

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("client_id", clientId);
            body.add("client_secret", clientSecret);
            body.add("code", code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                "https://github.com/login/oauth/access_token",
                request,
                Map.class
            );

            Map responseBody = tokenResponse.getBody();
            String accessToken = responseBody != null ? (String) responseBody.get("access_token") : null;

            System.out.println("Access token: " + accessToken);

            if (accessToken != null) {
            	if(email!=null)
            	{
            		User user=this.userService.find(email);
            		if(user != null)
            		{
            			user.setGitSourceToken(accessToken);
            			this.userService.update(user);
            			response.setContentType("text/html");
                    	response.getWriter().write("""
                    	    <html>
                    	    <body>
                    	    <script>
                    	        window.close();
                    	    </script>
                    	    <p>You can close this window.</p>
                    	    </body>
                    	    </html>
                    	""");
                    	return;
            		}
            		response.sendRedirect("http://localhost:4200/github-auth-failed");
            	}
            	response.sendRedirect("http://localhost:4200/github-auth-failed");
            } else {
                response.sendRedirect("http://localhost:4200/github-auth-failed");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @GetMapping("/api/github/token-status")
    public ResponseEntity<?> checkToken(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("tokenStatus","You Are Not Authorized"));
        }
        
        try {
            // Create RestTemplate to make GitHub API request
        	String email=jwt.getClaimAsString("email");
        	User user=this.userService.find(email);
        	String token=user.getGitSourceToken();
            RestTemplate restTemplate = new RestTemplate();
            
            // Set the Authorization header with the Bearer token
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            
            // Create an HttpEntity with the headers
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // Make a GET request to GitHub's /user endpoint to check the validity of the token
            String apiUrl = "https://api.github.com/user"; // GitHub API to get user details
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
            
            // If the response is successful, return that the token is valid
            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(Map.of("tokenStatus","Token is valid"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("tokenStatus","Token is invalid"));
            }
        } catch (Exception e) {
            // In case of error (e.g., network issues, expired token), return an invalid token response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("tokenStatus","Token is invalid"));
        }
    }

}
