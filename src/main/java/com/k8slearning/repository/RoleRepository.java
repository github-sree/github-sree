package com.k8slearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.k8slearning.model.Privilege;
import com.k8slearning.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

	@Query("SELECT p FROM Privilege p WHERE p.name=:privilegeName")
	public Privilege findPrivilegeByName(@Param("privilegeName") String privilegeNames);
}
