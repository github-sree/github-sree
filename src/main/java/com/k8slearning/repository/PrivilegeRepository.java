package com.k8slearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k8slearning.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, String>{

}
