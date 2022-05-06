package com.k8slearning.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k8slearning.api.PagingResponseApi;
import com.k8slearning.api.UserApi;
import com.k8slearning.model.UserEntity;
import com.k8slearning.service.UserService;
import com.k8slearning.utils.K8sUtils;

@RestController
@RequestMapping("/v1/auth")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private K8sUtils utils;

	@PostMapping("/user")
	public ResponseEntity<UserApi> createUser(@Valid @RequestBody UserApi userApi) {
		return new ResponseEntity<>(userService.createUser(userApi), HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<PagingResponseApi<UserApi>> retrieveUsers(final Pageable pageable) {
		Page<UserEntity> pageUser = userService.retrieveUsers(pageable);
		PagingResponseApi<UserApi> contents = new PagingResponseApi<>();

		contents.setTotalCount(pageUser.getTotalElements());
		contents.setPageNo(pageUser.getNumber());
		contents.setTotalPages(pageUser.getTotalPages());
		contents.setPageSize(pageUser.getNumberOfElements());
		contents.setRows(utils.mapStream(pageUser.get().map(page -> {
			page.setPassword(null);
			return page;
		}), UserApi.class));
		return new ResponseEntity<>(contents, HttpStatus.OK);
	}

	@PutMapping("/user/{userId}")
	public ResponseEntity<UserApi> updateUser(@PathVariable String userId, @Valid @RequestBody UserApi userApi) {
		return new ResponseEntity<>(userService.updateUser(userId, userApi), HttpStatus.OK);
	}
}
