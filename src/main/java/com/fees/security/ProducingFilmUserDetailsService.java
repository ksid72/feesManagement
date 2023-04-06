
package com.fees.security;

import com.fees.entity.User;
import com.fees.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ProducingFilmUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if (user != null) {
			return new ProducingFilmUserDetails(user);
		}
		
		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}

}
