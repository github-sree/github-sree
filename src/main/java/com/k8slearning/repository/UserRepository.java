package com.k8slearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.k8slearning.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

	@Query("SELECT a from UserEntity a WHERE a.userName=:userName")
	public UserEntity findByUserName(@Param("userName") String userName);
	
	@Query("SELECT COUNT(a.userId)>0 from UserEntity a")
	public boolean initialUserExists();
}
