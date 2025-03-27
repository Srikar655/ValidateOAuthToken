package com.iss.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.iss.Repos.UserRepository;
import com.iss.models.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> optionaluser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if(optionaluser.isPresent())
        {
        	User user=optionaluser.get();
	        Set<GrantedAuthority> authorities = user.getRoles().stream()
	                .map(role -> new SimpleGrantedAuthority(role.getName()))
	                .collect(Collectors.toSet());
	        return new org.springframework.security.core.userdetails.User(
	                usernameOrEmail,
	                 "",
	                authorities
	        );
        }
        return null;
    }
    public org.springframework.security.core.userdetails.UserDetails add(User user)
	{
		try
		{
			user=userRepository.save(user);
			Set<GrantedAuthority> authorities = user.getRoles().stream()
	                .map(role -> new SimpleGrantedAuthority(role.getName()))
	                .collect(Collectors.toSet());
			org.springframework.security.core.userdetails.User u= new org.springframework.security.core.userdetails.User(
	                user.getEmail(),
	                 "",
	                authorities
	        );
			return u;
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
    public User update(User user)
    {
    	if(userRepository.existsById(user.getId()))
    	{
    		return this.userRepository.save(user);
    	}
    	return null;
    }
    public Collection<? extends GrantedAuthority> getAuthorities(String email) {
        return userRepository.findRolesByUsernameOrEmail(email, email)
            .orElse(Collections.emptyList())  // Handle the case when roles are not found
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))  // Assuming the role entity has a getName() method
            .collect(Collectors.toList());
    }

}
