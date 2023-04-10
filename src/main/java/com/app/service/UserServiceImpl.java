package com.app.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.model.UserInfo;
import com.app.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository userRepo;
    @Autowired
	private BCryptPasswordEncoder passEncoder;

	@Override
	public Integer createUser(UserInfo userInfo) {

		userInfo.setPass(passEncoder.encode(userInfo.getPass()));

		return userRepo.save(userInfo).getId();
	}

	/*
	 * @Override public Optional<UserInfo> findByUsername(String username) {
	 * 
	 * return userRepo.findByUsername(username); }
	 */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			Optional<UserInfo> optional = userRepo.findByUsername(username);
			if (!optional.isPresent()) {
				throw new UsernameNotFoundException("User Not Present !");
			}else {
				UserInfo userInfo = optional.get();
				Set<String> roles = userInfo.getRoles();
				Set<GrantedAuthority> auth = new HashSet<>();
				for(String role:roles) {
					auth.add(new SimpleGrantedAuthority(role));				
				}
				return new User(username,userInfo.getPass(),auth);
			}
		
	}

}
