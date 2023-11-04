package com.k8slearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k8slearning.api.UserApi;
import com.k8slearning.service.RoleService;
import com.k8slearning.service.UserService;
import com.k8slearning.utils.Constants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/setup")
public class SetupController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@PostMapping("/user")
	public ResponseEntity<UserApi> setupUser(@Valid @RequestBody UserApi userApi) {
		if (userService.firstUserCreated()) {
			userApi.setUserId(userService.initialUserId());
			userApi.setMessage(Constants.ResponseStatus.INITIAL_USER_EXISTS);
			userApi.setStatus(Constants.Status.WARNING);
			return new ResponseEntity<>(userApi, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(userService.createUser(userApi), HttpStatus.OK);
	}

	@GetMapping("/exists")
	public ResponseEntity<Boolean> accountExists() {
		return new ResponseEntity<>(!userService.firstUserCreated() || !roleService.firstRoleCreated(), HttpStatus.OK);
	}
}
