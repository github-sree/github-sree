package com.k8slearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.k8slearning.model.UserEntity;
import com.k8slearning.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepo;

	@Override
	public List<UserEntity> getAllUser() {
		return userRepo.getAllUsers();
	}
}
