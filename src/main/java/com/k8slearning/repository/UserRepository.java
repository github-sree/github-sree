package com.k8slearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k8slearning.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

}
