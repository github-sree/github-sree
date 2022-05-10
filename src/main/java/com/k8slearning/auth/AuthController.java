package com.k8slearning.auth;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k8slearning.api.AuthRequestApi;
import com.k8slearning.api.UserApi;
import com.k8slearning.service.UserService;
import com.k8slearning.utils.K8sJwtUtils;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private K8sJwtUtils jwtUtils;
	Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody AuthRequestApi authApi) {
		System.out.println("into auth");
		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authApi.getUserName(), authApi.getPassWord()));
			SecurityContextHolder.getContext().setAuthentication(auth);
			AuthUserDetails userDetails = (AuthUserDetails) auth.getPrincipal();
			ResponseCookie resCookie = jwtUtils.generateJwtCookie(userDetails);
			logger.info("uesr ede {}", userDetails);
			return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookie.toString())
					.body(new UserApi(userDetails.getUsername()));
		} catch (DisabledException e) {
			logger.info("exception in auth  {}", e.getMessage());
			return new ResponseEntity<>("USER DISABLED", HttpStatus.UNAUTHORIZED);
		} catch (BadCredentialsException e) {
			logger.info("exception in auth  {}", e.getMessage());
			return new ResponseEntity<>("INVALID_CREDENTIALS", HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			logger.info("exception in auth  {}", e.getMessage());
			return null;
		}

	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("You've been signed out!");
	}
}
