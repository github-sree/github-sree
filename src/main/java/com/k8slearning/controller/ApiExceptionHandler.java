package com.k8slearning.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public void accessDeniedHandler(AccessDeniedException ae, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		final Map<String, Object> body = new HashMap<>();
		body.put("status", HttpServletResponse.SC_FORBIDDEN);
		body.put("error", "You don't have permission to access this resource, Please contact administrator");
		body.put("message", ae.getLocalizedMessage());
		body.put("path", request.getServletPath());
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), body);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public void accessDeniedHandler(DataIntegrityViolationException ae, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_CONFLICT);
		final Map<String, Object> body = new HashMap<>();
		body.put("status", HttpServletResponse.SC_CONFLICT);
		body.put("error", "Resource already created");
		body.put("message", ae.getLocalizedMessage());
		body.put("path", request.getServletPath());
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), body);
	}
}
