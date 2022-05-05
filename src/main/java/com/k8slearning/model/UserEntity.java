package com.k8slearning.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	private String userId;
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private boolean enabled;
	private boolean tokenExpired;
	@ManyToOne
	@JoinColumn(name = "role_id",referencedColumnName = "roleId")
	private Role role;

}
