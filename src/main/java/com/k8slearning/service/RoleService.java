package com.k8slearning.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.k8slearning.api.RoleApi;
import com.k8slearning.model.Role;

public interface RoleService {
	public RoleApi createRole(RoleApi roleApi);

	public Page<Role> retrieveRoles(Pageable pageable);

	public RoleApi updateRole(String roleId, RoleApi roleApi);

	public boolean firstRoleCreated();
}
