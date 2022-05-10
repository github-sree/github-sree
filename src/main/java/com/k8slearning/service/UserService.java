package com.k8slearning.service;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.k8slearning.api.UserApi;
import com.k8slearning.auth.AuthUserDetails;
import com.k8slearning.model.Role;
import com.k8slearning.model.UserEntity;
import com.k8slearning.repository.RoleRepository;
import com.k8slearning.repository.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	UserApi response = null;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AuthUserDetails userdetails = null;
		UserEntity userEntity = Optional.ofNullable(userRepository.findByUserName(username))
				.orElseThrow(() -> new UsernameNotFoundException("no user found.."));
		userdetails = new AuthUserDetails(userEntity);

		return userdetails;
	}

	public UserApi createUser(UserApi userApi) {
		UserApi response = null;
		try {
			UserEntity user = modelMapper.map(userApi, UserEntity.class);
			if (!userRepository.initialUserExists()) {
				user.setInitialUser(true);
			}
			user.setUserId(UUID.randomUUID().toString());
			user.setPassword(passwordEncoder.encode(userApi.getPassword()));
			userRepository.save(user);
			response = modelMapper.map(user, UserApi.class);
			response.setPassword(null);
		} catch (DataIntegrityViolationException e) {
			LOGGER.error("parameters are already exist..");
		} catch (Exception e) {
			LOGGER.error("exception while creating user::{}", e.getLocalizedMessage());
		}
		return response;
	}

	public Page<UserEntity> retrieveUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public UserApi updateUser(String userId, UserApi userApi) {
		Optional<UserEntity> userR = userRepository.findById(userId);
		userR.ifPresent(user -> {
			modelMapper.map(userApi, user);
			LOGGER.info("updated user details {}", user);
		});
		return null;
	}

	public UserApi assignRoleToUser(String userId, String roleId, boolean firstRoleAssign) {
		response = null;
		try {
			Optional<UserEntity> userR = userRepository.findById(userId);
			userR.ifPresent(user -> {
				Optional<Role> roleOp = roleRepository.findById(roleId);
				roleOp.ifPresent(role -> {
					user.setRole(role);
					userRepository.save(user);
					response = modelMapper.map(user, UserApi.class);
				});
			});
			response.setPassword(null);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("exception in assign role {}", e.getMessage());
		}
		return response;
	}

}
