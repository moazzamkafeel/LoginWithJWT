package com.app.jwt;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtToken {
	
	//-------------------------------------------------
	String key = "SKHsjkdlakshgoerpi3984dfjwetydgwe";
	
	
	public String buildToken(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuer("adit-it")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
				.signWith(SignatureAlgorithm.HS256 , key.getBytes())
				.compact();
	}
	
	
	public Claims getClaims(String token) {
		return  Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token)
				.getBody();
	}

	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}
	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}
	public boolean isTokenExp(String token) {
		Date expDate = getExpDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));
	}
	public boolean validateToken(String username, String token) {
		String tokenusername = getUserName(token);
		return(tokenusername.equals(username)&& !isTokenExp(token));
	}
	
	
	
}








