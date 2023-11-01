package com.k8slearning.auth;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k8slearning.api.AuthRequestApi;
import com.k8slearning.api.AuthResponseApi;
import com.k8slearning.utils.K8sJwtUtils;

@RestController
@RequestMapping("/v1")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private K8sJwtUtils jwtUtils;
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/signin")
	public ResponseEntity<Object> authenticate(@RequestBody AuthRequestApi authApi) {
		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authApi.getUserName(), authApi.getPassWord()));
			SecurityContextHolder.getContext().setAuthentication(auth);
			AuthUserDetails userDetails = (AuthUserDetails) auth.getPrincipal();
			ResponseCookie resCookie = jwtUtils.generateJwtCookie(userDetails);
			return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookie.toString())
					.body(generateResponse(userDetails));
		} catch (DisabledException e) {
			return new ResponseEntity<>("USER DISABLED", HttpStatus.UNAUTHORIZED);
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>("INVALID_CREDENTIALS", HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			logger.error("exception in auth  {}", e.getMessage());
			return null;
		}

	}

	private AuthResponseApi generateResponse(AuthUserDetails userDetails) {
		AuthResponseApi response = new AuthResponseApi();
		response.setFirstName(userDetails.getFirstName());
		response.setLastName(userDetails.getLastName());
		response.setUserName(userDetails.getUsername());
		response.setEmail(userDetails.getEmail());
		response.setRole(userDetails.getRole());
		response.setAuthorities(userDetails.getAuthorities().stream().map(p -> p.getAuthority().replace("ROLE_", ""))
				.collect(Collectors.toSet()));
		return response;
	}

	@PostMapping("/signout")
	public ResponseEntity<String> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("You've been signed out!");
	}
}
