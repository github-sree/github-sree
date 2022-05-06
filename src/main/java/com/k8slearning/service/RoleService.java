package com.k8slearning.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.k8slearning.api.RoleApi;
import com.k8slearning.model.Privilege;
import com.k8slearning.model.Role;
import com.k8slearning.repository.RoleRepository;

@Service
public class RoleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	ModelMapper modelMapper;

	public RoleApi createRole(RoleApi roleApi) {
		RoleApi response = null;
		try {
			Role roles = modelMapper.map(roleApi, Role.class);
			roles.setRoleId(UUID.randomUUID().toString());
			roles.setPrivileges(processPrivilege(roleApi.getPrivilegeNames()));
			roleRepository.save(roles);
			response = modelMapper.map(roles, RoleApi.class);
		} catch (Exception e) {
			LOGGER.info("exception roles save {}", e.getLocalizedMessage());
		}
		return response;
	}

	private Set<Privilege> processPrivilege(Set<String> privilegeNames) {
		Set<Privilege> privilegeSets = new HashSet<>();
		Optional.ofNullable(privilegeNames).ifPresent(pvSets -> {
			pvSets.forEach(pn -> {
				privilegeSets.add(roleRepository.findPrivilegeByName(pn));
			});
		});
		return privilegeSets;
	}

	public Page<Role> retrieveRoles(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

	public RoleApi updateRole(String roleId, RoleApi roleApi) {
		Optional<Role> roleOp = roleRepository.findById(roleId);
		roleOp.ifPresent(role -> {
			modelMapper.map(roleApi, role);
			LOGGER.info("updated user details {}", role);
		});
		return null;
	}

}
