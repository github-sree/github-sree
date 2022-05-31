package com.k8slearning.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.k8slearning.api.UserApi;
import com.k8slearning.model.UserEntity;

public interface UserService extends UserDetailsService {
	public UserApi createUser(UserApi userApi);

	public Page<UserEntity> retrieveUsers(Pageable pageable);

	public UserApi updateUser(String userId, UserApi userApi);

	public UserApi assignRoleToUser(String userId, String roleId);

	public boolean firstUserCreated();

	public String initialUserId();
}
