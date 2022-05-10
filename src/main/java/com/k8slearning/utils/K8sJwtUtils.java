package com.k8slearning.utils;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class K8sJwtUtils {

	@Value("${k8sapp.jwt.secret}")
	private String secret;
	@Value("${k8sapp.jwt.cookieName}")
	private String cookieName;
	@Value("${k8sapp.jwt.expirationMs}")
	private long expirationTime;
	private Logger logger = LoggerFactory.getLogger(K8sJwtUtils.class);

	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

	public String getUserNameFromJwtToken(String jwt) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody().getSubject();
	}

	public String getJwtFromCookies(HttpServletRequest request) {
		Optional<Cookie> cookieOp = Optional.ofNullable(WebUtils.getCookie(request, cookieName));
		if (cookieOp.isPresent()) {
			return cookieOp.get().getValue();
		}

		return null;
	}

	public ResponseCookie generateJwtCookie(UserDetails ud) {
		String jwt = generateTokenFromName(ud.getUsername());
		return ResponseCookie.from(cookieName, jwt).path("/v1").maxAge(24 * 60 * 60).httpOnly(true).build();
	}

	public ResponseCookie getCleanJwtCookie() {
		ResponseCookie cookie = ResponseCookie.from(cookieName, null).path("/v1").build();
		return cookie;
	}

	private String generateTokenFromName(String username) {
		Date issuedAt = new Date();
		return Jwts.builder().setSubject(username).setIssuedAt(issuedAt)
				.setExpiration(new Date(issuedAt.getTime() + expirationTime)).signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
}
