package com.iss.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iss.Dto.UserDto;
import com.iss.Mappers.UserMapper;
import com.iss.Repos.UserRepository;
import com.iss.models.User;


import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    	System.out.println("email is "+usernameOrEmail);
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email"));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        System.out.println(user.getEmail());
        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                 "",
                authorities
        );
    }
    public UserDto add(User user)
	{
		try
		{
			return UserMapper.Instance.toDto(userRepository.save(user));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
