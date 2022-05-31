package com.k8slearning.service.impl.v1;

import java.util.Optional;
import java.util.UUID;

import org.hibernate.Hibernate;
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
import com.k8slearning.service.UserService;
import com.k8slearning.utils.Constants;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private UserApi userResponse = new UserApi();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AuthUserDetails userdetails = null;
		UserEntity userEntity = Optional.ofNullable(userRepository.findByUserName(username))
				.orElseThrow(() -> new UsernameNotFoundException("no user found.."));
		Hibernate.initialize(userEntity.getRole().getPrivileges());
		userdetails = new AuthUserDetails(userEntity);

		return userdetails;
	}

	@Override
	public UserApi createUser(UserApi userApi) {
		try {
			UserEntity user = modelMapper.map(userApi, UserEntity.class);
			user.setInitialUser(false);
			if (!userRepository.initialUserExists()) {
				user.setInitialUser(true);
			}
			user.setUserId(UUID.randomUUID().toString());
			user.setPassword(passwordEncoder.encode(userApi.getPassword()));
			userRepository.save(user);
			userResponse = modelMapper.map(user, UserApi.class);
			userResponse.setPassword(null);
			userResponse.setMessage(Constants.ResponseStatus.USER_SAVED);
			userResponse.setStatus(Constants.Status.SUCCESS);
		} catch (DataIntegrityViolationException e) {
			LOGGER.error("parameters already exist..");
		} catch (Exception e) {
			LOGGER.error("exception while creating user::{}", e.getLocalizedMessage());
		}
		return userResponse;
	}

	@Override
	public Page<UserEntity> retrieveUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public UserApi updateUser(String userId, UserApi userApi) {
		Optional<UserEntity> userR = userRepository.findById(userId);
		userR.ifPresent(user -> {
			modelMapper.map(userApi, user);
			LOGGER.info("updated user details {}", user);
		});
		return null;
	}

	@Override
	public UserApi assignRoleToUser(String userId, String roleId) {
		try {
			Optional<UserEntity> userR = userRepository.findById(userId);
			userR.ifPresent(user -> {
				Optional<Role> roleOp = roleRepository.findById(roleId);
				roleOp.ifPresent(role -> {
					user.setRole(role);
					userRepository.save(user);
					userResponse = modelMapper.map(user, UserApi.class);
					userResponse.setPassword(null);
				});
			});
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("exception in assign role {}", e.getMessage());
		}
		return userResponse;
	}

	@Override
	public boolean firstUserCreated() {
		return userRepository.initialUserExists();
	}

	@Override
	public String initialUserId() {
		return userRepository.initialUserId();
	}

}
