package com.k8slearning.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.k8slearning.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

	@EntityGraph("graph.user-roles-privileges")
	@Query("SELECT a from UserEntity a WHERE a.userName=:userName")
	public UserEntity findByUserName(@Param("userName") String userName);

	@Query("SELECT COUNT(a.userId)>0 from UserEntity a WHERE a.initialUser=true")
	public boolean initialUserExists();

	@Query("SELECT COUNT(a.userId)>0 from UserEntity a WHERE a.userName=:userName")
	public boolean userNameExists(@Param("userName") String userName);

	@Query("SELECT COUNT(a.userId)>0 from UserEntity a WHERE a.email=:email")
	public boolean emailExists(@Param("email") String email);

	@Query("SELECT a.userId from UserEntity a WHERE a.initialUser=true")
	public String initialUserId();
}
