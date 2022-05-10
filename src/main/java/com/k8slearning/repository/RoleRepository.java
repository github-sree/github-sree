package com.k8slearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.k8slearning.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

	@Query("SELECT COUNT(r.roleId)>0 FROM Role r")
	public boolean roleEmpty();
}
