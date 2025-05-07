package com.iss.configs;



import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.iss.Validators.*;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.iss.Services.CustomUserDetailsService;
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableAsync
@EnableMethodSecurity
@EnableCaching
public class SecurityConfig {

	private final List<String> validAudiences;
	
	private final CustomUserDetailsService userservice;
	
	public SecurityConfig(CustomUserDetailsService userservice)
	{
		 validAudiences= Arrays.asList(
			        "950816388236-beh5nkicurvu1o30tcikbds4p7d481s4.apps.googleusercontent.com",
			        "141367274358-radhhb6jmms5eu9j743u5i2bfchkdt2f.apps.googleusercontent.com"
			    );
		 this.userservice=userservice;
	}
	@Bean
     CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));  
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));  
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));  
        corsConfig.setAllowCredentials(true);  
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig); 
        
        return source;
    }

	 @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));  // Use custom CORS configuration
	    	http
	                .authorizeHttpRequests(authorize -> authorize
	                                .requestMatchers("/api/**").authenticated()  // Protect specific endpoints
	                                .anyRequest().permitAll()  // Allow other requests (adjust as needed)
	                )
	                .oauth2ResourceServer(oauth2 -> oauth2
	                                .jwt(jwt -> jwt
	                                                .jwtAuthenticationConverter(jwtAuthenticationConverter()) // Convert JWT into authentication object
	                                )
	                );

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
               
            	UserDetails user=userservice.loadUserByUsername(email);
            	if(user!=null)
            	{
            		System.out.println(user.getAuthorities());
            		collection=user.getAuthorities();
            		System.out.println(collection);
            		
            	}
            	//collection=this.userservice.getAuthorities(email);
            } catch (UsernameNotFoundException e) {
            	
            	
            }
            
             return collection.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
        });

        return converter;
    }
    @Bean
     CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("users","userauthorities","courses","coursethumbnail","videos","taskimages","tasks","userCourses","usertasks","userVedios");
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)  // ⏱️ roles expire after 15 mins
            .maximumSize(1000));
        return cacheManager;
    }

}

