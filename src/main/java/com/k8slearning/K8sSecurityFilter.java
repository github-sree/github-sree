package com.k8slearning;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.k8slearning.service.UserService;
import com.k8slearning.utils.Constants;
import com.k8slearning.utils.K8sJwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class K8sSecurityFilter extends OncePerRequestFilter {

	private static Logger loggerFilter = LoggerFactory.getLogger(K8sSecurityFilter.class);

	@Autowired
	private K8sJwtUtils jwtUtils;
	@Autowired
	private UserService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String currentUri = request.getRequestURI();
		try {
			if (filterURI(currentUri)) {
				String jwt = parseJwt(request);
				if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
					String username = jwtUtils.getUserNameFromJwtToken(jwt);
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception e) {
			loggerFilter.error("Cannot set user authentication: {}", e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

	private boolean filterURI(String currentUri) {
		if (currentUri.contains(Constants.Api.WHILTELIST_SETUP_URL)) {
			return false;
		}
		if (currentUri.contains(Constants.Api.WHITELIST_AUTH_URL)) {
			return false;
		}
		if (currentUri.contains(Constants.Api.WHITELIST_H2_URL)) {
			return false;
		}
		return true;
	}

	private String parseJwt(HttpServletRequest request) {
		return jwtUtils.getJwtFromCookies(request);
	}

}
