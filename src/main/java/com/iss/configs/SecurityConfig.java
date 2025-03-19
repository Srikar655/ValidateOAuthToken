package com.iss.configs;



import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.iss.Validators.*;
import com.iss.models.User;
import com.iss.Repos.RoleRepository;
import com.iss.Services.CustomUserDetailsService;
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
public class SecurityConfig {

	private final List<String> validAudiences;
	private final RoleRepository roleRepos;
	
	private final CustomUserDetailsService userservice;
	
	public SecurityConfig(CustomUserDetailsService userservice,RoleRepository roleRepos)
	{
		 validAudiences= Arrays.asList(
			        "950816388236-beh5nkicurvu1o30tcikbds4p7d481s4.apps.googleusercontent.com",
			        "141367274358-radhhb6jmms5eu9j743u5i2bfchkdt2f.apps.googleusercontent.com"
			    );
		 this.userservice=userservice;
		 this.roleRepos = roleRepos;
	}
	@Bean
     CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));  // Allowed origins
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));  // Allowed methods
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));  // Allowed headers
        corsConfig.setAllowCredentials(true);  // Allow credentials (cookies, authorization headers)
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);  // Apply to all endpoints
        
        return source;
    }

	@Bean
    @Order(1)
    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher(new NegatedRequestMatcher(new AntPathRequestMatcher("/api/**")))
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable()) // Adjust based on your CSRF requirements
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .oauth2ResourceServer(oauth2 -> oauth2.disable()); // Disable JWT processing

        return http.build();
    }


	@Bean
	@Order(2)
	public SecurityFilterChain privateFilterChain(HttpSecurity http) throws Exception {
	    http
	        .securityMatcher("/api/**")
	        .authorizeHttpRequests(authz -> authz
	            .requestMatchers("/payment/webhook").permitAll() // Permit specific URLs
	            .anyRequest().authenticated() // All other requests under "/api/**" require authentication
	        )
	        .oauth2ResourceServer(oauth2 -> oauth2
	            .jwt(jwt -> jwt
	                .jwtAuthenticationConverter(jwtAuthenticationConverter()) // Enable JWT processing for OAuth2
	            )
	        )
	        .cors(cors -> cors.configurationSource(corsConfigurationSource()));

	    return http.build();
	}
    @Bean
     JwtDecoder jwtDecoder() {
        // Use Google's public keys for validating the JWT token
    	NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri("https://www.googleapis.com/oauth2/v3/certs").build();
        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(validAudiences);
        jwtDecoder.setJwtValidator(audienceValidator);
        
        return jwtDecoder;
    }
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String email = jwt.getClaim("email");
            Collection <? extends GrantedAuthority> collection=Collections.emptyList();

            try {
               
                collection=userservice.getAuthorities(email);
            } catch (UsernameNotFoundException e) {

            	
            }
            
             return collection.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
        });

        return converter;
    }


}

