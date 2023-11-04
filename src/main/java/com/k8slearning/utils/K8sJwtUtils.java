package com.k8slearning.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class K8sJwtUtils {

	@Value("${k8sapp.jwt.secret}")
	private String secret;
	@Value("${k8sapp.jwt.cookieName}")
	private String cookieName;
	@Value("${k8sapp.jwt.expirationMs}")
	private long expirationTime;
	private static final Logger LOGGER = LoggerFactory.getLogger(K8sJwtUtils.class);

	private SecretKey SECRET_KEY;

	@PostConstruct
	public void init() {
		String encodedString = Base64.getEncoder().encodeToString(secret.getBytes());
		SECRET_KEY = Keys.hmacShaKeyFor(encodedString.getBytes(StandardCharsets.UTF_8));
	}

	public boolean validateJwtToken(String token) {
		try {
			LOGGER.info("incoming token::{}", token);
			Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
			return true;
		} catch (JwtException e) {
			LOGGER.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

	public String getUserNameFromJwtToken(String jwt) {
		LOGGER.info("subject value is::{}",
				Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(jwt).getPayload().getSubject());
		return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(jwt).getPayload().getSubject();
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
		return ResponseCookie.from(cookieName, jwt).path("/").maxAge(24 * 60 * 60).httpOnly(true).build();
	}

	public ResponseCookie getCleanJwtCookie() {
		return ResponseCookie.from(cookieName, "").path("/").maxAge(0).build();
	}

	private String generateTokenFromName(String username) {
		Date issuedAt = new Date();

		return Jwts.builder().subject(username).issuedAt(issuedAt)
				.expiration(new Date(issuedAt.getTime() + expirationTime)).signWith(SECRET_KEY).compact();
	}
}
