package com.k8slearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.k8slearning.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, String> {
	@Query("SELECT p FROM Privilege p WHERE p.name=:privilegeName")
	public Privilege findPrivilegeByName(@Param("privilegeName") String privilegeNames);

}
