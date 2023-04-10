package com.app.controller;

import java.security.Principal;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.jwt.JwtToken;
import com.app.model.UserInfo;
import com.app.request.UserRequest;
import com.app.response.UserResponse;
import com.app.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private IUserService userService;
	@Autowired
	private JwtToken jwtToken;

	@PostMapping("/dashboard")
	public ResponseEntity<String> dashPage(Principal p){
		return ResponseEntity.ok("Welcome To Dashboard ["+p.getName() +" ]");
	}
	
	@PostMapping("/save")
	public ResponseEntity<String> createUser(@RequestBody UserInfo userInfo) {
		Integer userId = userService.createUser(userInfo);
		return ResponseEntity.ok("success" + userId);
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest) {
		
		// check for Authentication
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								userRequest.getUsername(), 
								userRequest.getUserpass()));

				// set as SecurityContext(Authentication)
				SecurityContextHolder.getContext().setAuthentication(authentication);

				// Generate JWT Token
				String jwt = jwtToken.buildToken(authentication);

				//current user object
				UserDetails userDetails= (UserDetails) authentication.getPrincipal();

				// return response
				return ResponseEntity.ok(
						new UserResponse(
								jwt, //token
								userDetails.getUsername(), //username
								userDetails.getAuthorities()
								.stream()
								.map(auth->auth.getAuthority())
								.collect(Collectors.toSet()) //Set<String>
								)
						);
		//git remote add origin https://github.com/moazzamkafeel/LoginWithJWT.git
	}
}
