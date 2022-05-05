package com.k8slearning.repository;

import java.util.List;

import com.k8slearning.model.UserEntity;

public interface UserRepository {

	List<UserEntity> getAllUsers();

}
