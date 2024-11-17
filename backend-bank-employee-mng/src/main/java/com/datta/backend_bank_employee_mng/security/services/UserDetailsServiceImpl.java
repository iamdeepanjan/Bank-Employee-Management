package com.datta.backend_bank_employee_mng.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.datta.backend_bank_employee_mng.models.User;
import com.datta.backend_bank_employee_mng.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with username: "+username));
		return UserDetailsImpl.build(user);
	}
	
	
}
