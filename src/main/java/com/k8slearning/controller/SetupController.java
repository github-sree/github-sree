package com.k8slearning.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k8slearning.api.RoleApi;
import com.k8slearning.api.UserApi;
import com.k8slearning.service.RoleService;
import com.k8slearning.service.UserService;

@RestController
@RequestMapping("/v1/setup")
public class SetupController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@PostMapping("/role")
	public ResponseEntity<RoleApi> setupRole(@Valid @RequestBody RoleApi roleApi) {
		return new ResponseEntity<>(roleService.setupFirstRole(roleApi), HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<UserApi> setupUser(@Valid @RequestBody UserApi userApi) {
		return new ResponseEntity<>(userService.createUser(userApi), HttpStatus.OK);
	}

	@PutMapping("/assign-role/{userId}/{roleId}")
	public ResponseEntity<UserApi> assignRole(@PathVariable String userId, @PathVariable String roleId) {
		return new ResponseEntity<>(userService.assignRoleToUser(userId, roleId,true), HttpStatus.OK);
	}
}
