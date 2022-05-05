package com.k8slearning.service;

import java.util.List;

import com.k8slearning.model.UserEntity;

public interface UserService {
	List<UserEntity> getAllUser();
}
